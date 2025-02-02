let languageIndex = 0;
let conversationName = "Conversación 1";
let darkMode = false;
let sidebarVisible = true;

function updateLanguage() {
    languageIndex = document.getElementById('language').selectedIndex;
}

function switchConversation() {
    conversationName = document.getElementById('conversation-select').value;
    loadConversation();
}

function newConversation() {
    const name = `Conversación ${Object.keys(conversations).length + 1}`;
    conversations[name] = [];
    const select = document.getElementById('conversation-select');
    const option = document.createElement('option');
    option.value = name;
    option.textContent = name;
    select.appendChild(option);
    conversationName = name;
    loadConversation();
}

function toggleDarkMode() {
    darkMode = !darkMode;
    document.body.classList.toggle('dark-mode');
}

function toggleSidebar() {
    sidebarVisible = !sidebarVisible;
    const sidebar = document.getElementById('sidebar');
    sidebar.classList.toggle('hidden'); // Agregar o eliminar la clase 'hidden'
}

async function sendMessage() {
    const userInput = document.getElementById('user-input').value;
    if (!userInput.trim()) return;

    const chatHistory = document.getElementById('chat-history');
    chatHistory.innerHTML += `<div class="message user">Tú: ${userInput}</div>`;
    document.getElementById('loading').style.display = 'block';

    const response = await fetch('/get_response', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ input: userInput, language: languageIndex, conversation: conversationName })
    });

    const data = await response.json();
    chatHistory.innerHTML += `<div class="message assistant">Modelo: ${data.response}</div>`;
    document.getElementById('user-input').value = '';
    document.getElementById('loading').style.display = 'none';

    if (conversations[conversationName]) {
        conversations[conversationName].push({ role: 'user', content: userInput });
        conversations[conversationName].push({ role: 'assistant', content: data.response });
    }
}

function loadConversation() {
    const chatHistory = document.getElementById('chat-history');
    chatHistory.innerHTML = '';
    if (conversations[conversationName]) {
        conversations[conversationName].forEach(msg => {
            const className = msg.role === 'user' ? 'user' : 'assistant';
            chatHistory.innerHTML += `<div class="message ${className}">${msg.role === 'user' ? 'Tú:' : 'Modelo:'} ${msg.content}</div>`;
        });
    }
}