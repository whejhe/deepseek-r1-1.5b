import streamlit as st
import ollama

# Configuración inicial de la interfaz
st.set_page_config(page_title="DeepSeek Chat", layout="wide")
st.title('💬 Chat con DeepSeek')
st.caption("Interfaz profesional para interactuar con DeepSeek en local")

# Inicializar variables de estado
if 'show_thinking' not in st.session_state:
    st.session_state.show_thinking = False
if 'history' not in st.session_state:
    st.session_state.history = []

# Barra lateral para controles
# with st.sidebar:
#     st.header("Controles")
#     st.session_state.show_thinking = st.checkbox(
#         "Mostrar línea de pensamiento",
#         value=st.session_state.show_thinking
#     )
#     st.markdown("---")
#     if st.button("Limpiar historial"):
#         st.session_state.history = []

# Caja de entrada de texto
user_input = st.text_input('Escribe tu Pregunta:')

if st.button('Enviar'):
    if user_input.strip():
        full_response = ""
        for chunk in ollama.chat(model='deepseek-r1:1.5b', 
                                 messages=[{'role': 'user', 'content': user_input}], 
                                 stream=True):
            if "message" in chunk:
                full_response += chunk['message']['content']
                st.write(full_response) # Mostrar la respuesta en la interfaz de usuario