import g4f
from flask import Flask, request


def ask_gpt(prompt: str) -> str:
    response = g4f.ChatCompletion.create(
        model="gpt-3.5-turbo",
        messages=[{"role": "user",
                   "content": prompt}],
    )
    return response


app = Flask(__name__)


@app.route('/', methods=['POST'])
def hello_world():
    content = request.get_json()
    print(content['message'])
    generated_message = ask_gpt(content['message'])

    return {'message': generated_message}


app.run(host='0.0.0.0', port=8090)




# print(ask_gpt("В каком году был детский крестовый поход и чем он закончился?"))
