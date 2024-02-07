import os
import hashlib
import base64

# 获取当前脚本所在路径
script_path = os.path.dirname(os.path.abspath(__file__))
mods_path = os.path.join(script_path, "mods")
hash_file = os.path.join(script_path, "hash.txt")

# 删除之前的 hash.txt 文件（如果存在）
if os.path.exists(hash_file):
    os.remove(hash_file)

# 遍历 mods 文件夹下所有 .jar 文件，并计算哈希值
with open(hash_file, 'a') as hash_file:
    for filename in os.listdir(mods_path):
        if filename.endswith(".jar"):
            file_path = os.path.join(mods_path, filename)
            
            # 计算文件的 SHA256 哈希值
            with open(file_path, 'rb') as f:
                sha256_hash = hashlib.sha256()
                for byte_block in iter(lambda: f.read(4096), b""):
                    sha256_hash.update(byte_block)
                hash_value = sha256_hash.hexdigest()

            # 进行 Base64 编码
            base64_hash = base64.b64encode(hash_value.encode('utf-8')).decode('utf-8')

            # 将 Base64 编码后的哈希值写入文件
            hash_file.write(base64_hash + '\n')