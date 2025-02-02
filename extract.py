import os
import json

# Carpeta donde están los archivos .java
folder_path = "android_code"

# Salida del dataset
output_file = "dataset/android_code.jsonl"

# Procesar cada archivo .java
with open(output_file, "w", encoding="utf-8") as f_out:
    for root, dirs, files in os.walk(folder_path):
        for file in files:
            if file.endswith(".java"):
                file_path = os.path.join(root, file)
                with open(file_path, "r", encoding="utf-8") as f_in:
                    code = f_in.read()
                    # Guardar en formato JSONL
                    f_out.write(json.dumps({"text": code}) + "\n")