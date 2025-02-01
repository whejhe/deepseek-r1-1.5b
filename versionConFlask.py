from flask import Flask, request, render_template_string, jsonify
import ollama

app = Flask(__name__)

# Selector de idioma
languages = {
    "Español": "Responde en español: ",
    "Inglés": "Answer in English: ",
    "Italiano": "Rispondi in italiano: ",
    "Francés": "Réponds en français: ",
    "Chino": "回答用中文: "
}

@app.route('/')
def index():
    return render_template_string('''
        <!doctype html>
        <html lang="en">
        <head>
            <meta charset="utf-8">
            <title>DeepSeek Chat</title>
            <style>
                body { font-family: Arial, sans-serif; margin: 0; padding: 0; }
                .container { max-width: 800px; margin: 50px auto; padding: 20px; border: 1px solid #ccc; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }
                h1 { text-align: center; }
                select { width: 100%; padding: 10px; margin-bottom: 20px; }
                textarea { width: 100%; height: 100px; padding: 10px; margin-bottom: 20px; }
                button { width: 100%; padding: 10px; background-color: #007bff; color: white; border: none; border-radius: 5px; cursor: pointer; }
                button:hover { background-color: #0056b3; }
                .message { margin-bottom: 20px; padding: 10px; border-radius: 5px; }
                .user { background-color: #f0f0f0; text-align: right; }
                .assistant { background-color: #e0ffe0; text-align: left; }
            </style>
        </head>
        <body>
            <div class="container">
                <h1>Chat con DeepSeek</h1>
                <select id="language" onchange="updateLanguage()">
                    {% for lang in languages %}
                        <option value="{{ loop.index0 }}">{{ lang }}</option>
                    {% endfor %}
                </select>
                <textarea id="user-input" placeholder="Escribe tu pregunta aquí..."></textarea>
                <button onclick="sendMessage()">Enviar</button>
                <div id="chat-history"></div>
            </div>

            <script>
                let languageIndex = 0;
                function updateLanguage() {
                    languageIndex = document.getElementById('language').selectedIndex;
                }

                async function sendMessage() {
                    const userInput = document.getElementById('user-input').value;
                    if (!userInput.trim()) return;

                    const chatHistory = document.getElementById('chat-history');
                    chatHistory.innerHTML += `<div class="message user">Tú: ${userInput}</div>`;

                    const response = await fetch('/get_response', {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify({ input: userInput, language: languageIndex })
                    });

                    const data = await response.json();
                    chatHistory.innerHTML += `<div class="message assistant">Modelo: ${data.response}</div>`;
                    document.getElementById('user-input').value = '';
                }
            </script>
        </body>
        </html>
    ''', languages=list(languages.keys()))

@app.route('/get_response', methods=['POST'])
def get_response():
    data = request.json
    user_input = data['input']
    selected_language_index = data['language']
    selected_language = list(languages.values())[selected_language_index]
    
    final_prompt = f"{selected_language}{user_input}"
    full_response = ""

    for chunk in ollama.chat(
        model='deepseek-r1:7b',
        messages=[{'role': 'user', 'content': final_prompt}],
        stream=True
    ):
        if "message" in chunk and "content" in chunk["message"]:
            content = chunk['message']['content']
            full_response += content

    return jsonify({'response': full_response})

if __name__ == '__main__':
    app.run(debug=True)