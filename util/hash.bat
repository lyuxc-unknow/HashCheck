@echo off

rem 获取当前脚本所在路径
set "script_path=%~dp0"
set "mods_path=%script_path%mods"

REM 删除之前的 hash.txt 文件（如果存在）
del hash.txt 2>nul

rem 遍历 mods 文件夹下所有 .jar 文件，并计算哈希值
for %%f in ("%mods_path%\*.jar") do (
    certutil -hashfile "%%f" SHA256 | findstr /r "^[0-9a-f]*$" >> "%script_path%hash.txt"
)
