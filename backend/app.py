from flask import Flask, jsonify, request

app = Flask(__name__)


@app.route('/', methods=['GET', 'POST'])
def get_glucose():
    return "Hello World!"


@app.route('/', methods=['GET', 'POST'])
def suggest():
    return "Hello World!"


if __name__ == "__main__":
    app.run(host="127.0.0.1", port=80, debug=True)
