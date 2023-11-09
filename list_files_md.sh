#!/bin/bash

# 用于递归地遍历目录并以Markdown格式打印其结构
function list_files() {
    local folder=$1
    local indent=$2
    for file in "$folder"/*; do
        # 如果是目录，则递归地调用此函数
        if [ -d "$file" ]; then
            echo "${indent}- **$(basename "$file")/**"
            list_files "$file" "${indent}  "
        else
            echo "${indent}- $(basename "$file")"
        fi
    done
}

# 开始运行脚本
echo "# Project Directory Structure"
list_files . ""