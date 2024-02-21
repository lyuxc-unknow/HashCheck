import os
import hashlib
import base64
# ChatGPT写的
# 完全没手写
# 获取当前脚本所在路径
script_path = os.path.dirname(os.path.abspath(__file__))
folders_to_hash = ["mods", "scripts", "kubejs/client_scripts", "kubejs/server_scripts", "kubejs/startup_scripts"]
hash_file = os.path.join(script_path, "hash.txt")

# 删除之前的 hash.txt 文件（如果存在）
if os.path.exists(hash_file):
    os.remove(hash_file)
# 定义递归函数来遍历文件夹及其子文件夹中的文件
def hash_files_in_folder(folder_path, hash_file):
    for root, dirs, files in os.walk(folder_path):
        for filename in files:
            file_path = os.path.join(root, filename)

            # 计算文件的 SHA256 哈希值
            with open(file_path, 'rb') as f:
                sha256_hash = hashlib.sha256()
                for byte_block in iter(lambda: f.read(4096), b""):
                    sha256_hash.update(byte_block)
                hash_value = sha256_hash.hexdigest()

            # 进行 Base64 编码
            base64_hash = base64.b64encode(hash_value.encode('utf-8')).decode('utf-8')

            # 将 Base64 编码后的哈希值写入文件
            with open(hash_file, 'a') as hf:
                hf.write(base64_hash + '\n')

# 遍历指定文件夹及其子文件夹下所有文件，并计算哈希值
for folder in folders_to_hash:
    folder_path = os.path.join(script_path, folder)
    if not os.path.exists(folder_path):
        continue  # 如果文件夹不存在，则跳过
    hash_files_in_folder(folder_path, hash_file)
