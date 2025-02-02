# extract_android_code.py: Script para extraer el código fuente de archivos .java, .xml, .gradle, etc. en un dataset JSONL
import os
import json
import hashlib

# Carpeta donde están los archivos
folder_path = "android_code"

# Salida del dataset
output_file = "dataset/android_code.jsonl"

# Extensiones de archivo de interés
file_extensions = [".java", ".xml", ".gradle", ".kt", ".yaml"]

# Conjunto para almacenar hashes únicos
unique_hashes = set()

# Función para calcular el hash de un fragmento de código
def calculate_hash(content):
    return hashlib.sha256(content.encode('utf-8')).hexdigest()

# Función para verificar si el contenido es relevante
def is_relevant_content(file_extension, content):
    # Palabras clave específicas por extensión
    keywords_by_extension = {
        ".java": ["Activity", "Fragment", "RecyclerView", "onCreate", "Intent", "Bundle"],
        ".xml": ["<layout", "<activity", "<application", "<service", "<intent-filter"],
        ".gradle": ["dependencies", "implementation", "android {", "buildTypes"],
        ".kt": ["class", "fun", "val", "var", "override"],
        ".yaml": ["services:", "environment:", "volumes:"]
    }
    
    # Obtener palabras clave para la extensión actual
    keywords = keywords_by_extension.get(file_extension.lower(), [])
    
    # Si no hay palabras clave definidas, asumimos que es relevante
    if not keywords:
        return True
    
    # Verificar si alguna palabra clave está presente en el contenido
    return any(keyword in content for keyword in keywords)

# Procesar cada archivo
with open(output_file, "w", encoding="utf-8") as f_out:
    for root, dirs, files in os.walk(folder_path):
        for file in files:
            # Filtrar por extensiones de interés
            if any(file.endswith(ext) for ext in file_extensions):
                file_path = os.path.join(root, file)
                try:
                    with open(file_path, "r", encoding="utf-8") as f_in:
                        content = f_in.read()
                        
                        # Calcular el hash del contenido
                        content_hash = calculate_hash(content)
                        
                        # Verificar si el contenido ya existe en el conjunto de hashes
                        if content_hash not in unique_hashes and is_relevant_content(os.path.splitext(file)[1], content):
                            unique_hashes.add(content_hash)
                            
                            # Guardar en formato JSONL
                            f_out.write(json.dumps({"text": content}) + "\n")
                except Exception as e:
                    print(f"Error al procesar el archivo {file_path}: {e}")