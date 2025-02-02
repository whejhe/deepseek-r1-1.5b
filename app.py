from flask import Flask, request, render_template, jsonify
import ollama
import re

app = Flask(__name__, static_url_path='/static')

# Selector de idioma
languages = {
    "Español": "Responde en español: ",
    "Inglés": "Answer in English: ",
    "Italiano": "Rispondi in italiano: ",
    "Francés": "Réponds en français: ",
    "Chino": "回答用中文: "
}

# Variables globales para manejar conversaciones y modo oscuro
conversations = {"Conversación 1": []}
dark_mode = False
sidebar_visible = True

@app.route('/')
def index():
    return render_template('index.html', languages=languages, conversations=conversations, sidebar_visible=sidebar_visible)

@app.route('/get_response', methods=['POST'])
def get_response():
    data = request.json
    user_input = data['input']
    selected_language_index = data['language']
    conversation_name = data['conversation']
    
    # Seleccionar el idioma
    selected_language = list(languages.values())[selected_language_index]
    final_prompt = f"{selected_language}{user_input}"
    full_response = ""

    # Generar respuesta del modelo
    for chunk in ollama.chat(
        model='deepseek-r1:7b',
        messages=[{'role': 'user', 'content': final_prompt}],
        stream=True
    ):
        if "message" in chunk and "content" in chunk["message"]:
            content = chunk['message']['content']
            full_response += content
    
    # Función para detectar y formatear bloques de código
    def format_code_blocks(response):
        # Expresión regular para identificar bloques de código (```lenguaje\ncontenido```)
        code_block_pattern = r"(```\w*\n(.*?)```)"
        # Reemplazar cada bloque de código con su versión formateada
        formatted_response = re.sub(
            code_block_pattern,
            lambda match: f"<pre><code>{match.group(1)}</code></pre>",
            response,
            flags=re.DOTALL  # Permitir que el punto (`.`) coincida con saltos de línea
        )
        return formatted_response

    # Formatear la respuesta antes de devolverla
    formatted_response = format_code_blocks(full_response)

    # Guardar respuesta en la conversación actual
    if conversation_name in conversations:
        conversations[conversation_name].append({'role': 'assistant', content: formatted_response})

    return jsonify({'response': formatted_response})

if __name__ == '__main__':
    app.run(debug=True)