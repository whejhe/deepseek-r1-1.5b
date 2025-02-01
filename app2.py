import streamlit as st
import ollama

# Configuración inicial de la interfaz
st.set_page_config(page_title="DeepSeek Chat", layout="wide")
st.title('💬 Chat con DeepSeek')
st.caption("Interfaz profesional para interactuar con DeepSeek en local")

# Selector de idioma en la cabecera
languages = {
    "Español": "Responde en español: ",
    "Inglés": "Answer in English: ",
    "Italiano": "Rispondi in italiano: ",
    "Francés": "Réponds en français: ",
    "Chino": "回答用中文: "
}
selected_language = st.selectbox(
    "Selecciona el idioma de respuesta",
    options=list(languages.keys()),
    index=0  # Valor predeterminado: Español
)

# Inicializar variables de estado
if 'show_thinking' not in st.session_state:
    st.session_state.show_thinking = False
if 'history' not in st.session_state:
    st.session_state.history = []

# Barra lateral para controles
with st.sidebar:
    st.header("Controles")
    st.session_state.show_thinking = st.checkbox(
        "Mostrar línea de pensamiento",
        value=st.session_state.show_thinking
    )
    st.markdown("---")
    if st.button("Limpiar historial"):
        st.session_state.history = []

# Contenedor principal del chat
chat_container = st.container()

# Entrada de usuario
with st.form(key='input_form'):
    user_input = st.text_input(
        "Escribe tu pregunta:",
        key="user_input",
        placeholder="¿Cómo puedo ayudarte hoy?",
        value=""
    )
    col1, col2 = st.columns([1, 6])
    with col1:
        submit_button = st.form_submit_button('Enviar ➤')

# Procesamiento de la respuesta
if submit_button and user_input.strip():
    # Añadir pregunta al historial
    st.session_state.history.append(('user', user_input))
    
    # Generar respuesta
    full_response = ""
    thinking_log = []
    with st.spinner('Procesando tu consulta...'):
        # Construir el prompt con el idioma seleccionado
        language_instruction = languages[selected_language]
        final_prompt = f"{language_instruction}{user_input}"
        
        for chunk in ollama.chat(
            model='deepseek-r1:1.5b',
            messages=[{'role': 'user', 'content': final_prompt}],
            stream=True
        ):
            if "message" in chunk and "content" in chunk["message"]:
                content = chunk['message']['content']
                full_response += content
                thinking_log.append(content)
    
    # Añadir respuesta al historial
    st.session_state.history.append(('assistant', full_response))

# Mostrar historial del chat
with chat_container:
    for role, content in st.session_state.history:
        if role == 'user':
            with st.chat_message(name="user", avatar="👨‍💻"):  # Avatar del usuario
                st.markdown(content)
        else:
            with st.chat_message(name="assistant", avatar="🤖"):  # Avatar del modelo
                st.markdown(content)
                
                # Contenedor de pensamiento condicional
                if st.session_state.show_thinking and thinking_log:
                    with st.expander("Proceso de pensamiento", expanded=False):
                        st.caption("Análisis paso a paso del modelo:")
                        thinking_container = st.container()
                        with thinking_container:
                            st.markdown(
                                f"<div style='background: #f5f5f5; border-radius: 5px; "
                                f"padding: 15px; margin: 10px 0;'>"
                                f"{''.join(thinking_log)}</div>",
                                unsafe_allow_html=True
                            )

# Estilos CSS personalizados
st.markdown("""
    <style>
    .stChatMessage {padding: 1.5rem; border-radius: 0.5rem; margin: 1rem 0;}
    .stChatMessage [data-testid="stMarkdownContainer"] {font-size: 1.1rem;}
    .stButton button {width: 100%; border: none; background: #007bff; color: white;}
    .stButton button:hover {background: #0056b3;}
    .stTextInput input {padding: 1rem !important;}
    </style>
""", unsafe_allow_html=True)