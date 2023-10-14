import g4f
from flask import Flask, request, jsonify
from g4f.Provider import (
    AItianhu,
    Acytoo,
    Aichat,
    Ails,
    Bard,
    Bing,
    ChatBase,
    ChatgptAi,
    H2o,
    HuggingChat,
    OpenAssistant,
    OpenaiChat,
    Raycast,
    Theb,
    Vercel,
    Vitalentum,
    Ylokh,
    You,
    Yqcloud,
)


def ask_gpt(prompt: str) -> str:
    response = g4f.ChatCompletion.create(
        model="gpt-3.5-turbo",
        provider=g4f.Provider.Aichat,
        messages=[{"role": "user",
                   "content": prompt}],
    )
    return response


app = Flask(__name__)


@app.route('/', methods=['POST'])
def generate_article():
    content = request.get_json()

    message = "Я хочу, чтобы ты выступил в роли учителя истории. Ты будешь исследовать и анализировать культурные, " \
         "экономические, политические и социальные события прошлого, собирать данные из первоисточников и " \
         "использовать их для разработки теорий о том, что происходило в различные периоды истории.\n" \
         "Тема: " + content['title'] + ".\n" \
         "Ключевые слова: " + content['content'] + "\n" \
         "Пиши увлекательно и информативно\n" \
         "Заверши текст выводом, наводящим на размышления\n"

    print(message)
    generated_message = str(ask_gpt(message))
    print(generated_message)
    return jsonify(
        message=generated_message
    )


app.run(host='0.0.0.0', port=8090)
