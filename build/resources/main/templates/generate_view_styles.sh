#!/bin/bash

OUTPUT_DIR="theme/styleTextViews"
mkdir -p "$OUTPUT_DIR"

# TextViewStyleLookup 显式字段及推荐值（包含嵌套 PaddingKey）
FIELDS=(
  "fontKey:textFieldFont"
  "textColorKey:textColor"
  "placeholderColorKey:placeholderTextColor"
  "backgroundColorKey:textFieldBackgroundColor"
  "borderColorKey:textFieldBorderColor"
  "borderWidth:borderWidthThin"
  "cornerRadius:cornerRadiusSmall"
  "isEditable:true"
  "lineSpacing:textViewLineSpacing"
)

# Padding 单独定义（必须为嵌套结构）
PADDING_KEYS=(
  "top:paddingTop"
  "left:paddingLeft"
  "bottom:paddingBottom"
  "right:paddingRight"
)

# 所有 TextViewStyleKey
keys=(
  editorPrimary
  editorSecondary
  commentInput
  feedbackInput
  noteInput
  captionInput
  popupInput
  formInput
  richTextEditor
  markdownEditor
  readonlyDisplay
  disabledTextView
  diaryEditor
  questionInput
)

for name in "${keys[@]}"; do
  FILE="$OUTPUT_DIR/$name.json"
  echo "{" > "$FILE"

  total=${#FIELDS[@]}
  for ((i = 0; i < total; i++)); do
    kv="${FIELDS[$i]}"
    key="${kv%%:*}"
    value="${kv##*:}"

    if [[ "$value" == "null" ]]; then
      echo "  \"${key}\": null," >> "$FILE"
    elif [[ "$value" == "true" || "$value" == "false" ]]; then
      echo "  \"${key}\": ${value}," >> "$FILE"
    else
      echo "  \"${key}\": \"${value}\"," >> "$FILE"
    fi
  done

  # 添加嵌套 padding
  echo "  \"padding\": {" >> "$FILE"
  for ((j = 0; j < ${#PADDING_KEYS[@]}; j++)); do
    pk="${PADDING_KEYS[$j]}"
    pkey="${pk%%:*}"
    pval="${pk##*:}"
    comma=$([[ $j -lt $((${#PADDING_KEYS[@]} - 1)) ]] && echo "," || echo "")
    echo "    \"${pkey}\": \"${pval}\"$comma" >> "$FILE"
  done
  echo "  }" >> "$FILE"

  echo "}" >> "$FILE"
  echo "✅ Created: $FILE"
done