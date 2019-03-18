from flask import Flask, jsonify, request
from createMockData import Solution
import pandas as pd

# from flask import session

app = Flask(__name__)

'''
Food Type:
    Rice   250g
    Banana 100g
    Chicken 100g
Exercise Type:
    Run
    Walk
    Bike

'''
session = {}

@app.before_first_request
def activate_job():
    print('hahaha')
    session['time'] = 0
    session['level'] = 7.0
    session['target'] = 7.0

    session['condition'] = {
        'run':session['target']+0.8,
        'walk':session['target']+0.5,
        'bike':session['target']+0.3,
        'rice':session['target']-0.8,
        'banana':session['target']-0.5,
        'chicken':session['target']-0.4
    }
    session['speed'] = {
        'run': -0.02,
        'walk': -0.009,
        'bike': -0.01
    }


    '''
    Set default rate for different event:
    If time > 22:00 PM or time < 7:00AM:
        suggest sleep
    Exercise:
        If level > target + 2.0
            run   -0.02
        If level > target + 0.9
            walk  -0.009
        If level > target + 0.5
            bike  -0.01
    Food:
        If level < target - 2.0
            rice 200g
        If level < target - 1.2
            banana 100g
        If level < target - 0.5
            chicken 100g
    '''



@app.route('/sugar', methods=['GET', 'POST'])
def get_glucose():
    session['time'] += 1

    df = pd.read_csv("data.csv")
    s = Solution(df)

    curTime = s.df.loc[session['time']]['time']
    curLevel = None
    if s.df.loc[session['time']]['type'] == 'sugar':
        curLevel = s.df.loc[session['time']]['event']
    else:
        curLevel = s.findLevel(session['time'])
    session['level'] = float(curLevel)
    response = {
        'time': curTime,
        'level': curLevel
    }
    return jsonify(response)


@app.route('/suggest', methods=['GET'])
def suggest():
    suggest = {
        "type": "success",
        "suggest": {
            "type": "",
            "event": "",
            "amount": 0,
            "unit": ""
        }
    }
    print(session['level'], session['condition'])
    suggest["type"] = "success"

    if session['level'] > session['condition']['run']:
        suggest["suggest"]["type"] = "exercise"
        suggest["suggest"]["event"] = "run"
        suggest["suggest"]["amount"] = abs(int((session['level']-session['condition']['run'])/session['speed']['run']))
        suggest["suggest"]["unit"] = "min"
    elif session['level'] > session['condition']['bike']:
        suggest["suggest"]["type"] = "exercise"
        suggest["suggest"]["event"] = "bike"
        suggest["suggest"]["amount"] = abs(int((session['level']-session['condition']['bike'])/session['speed']['bike']))
        suggest["suggest"]["unit"] = "min"
    elif session['level'] > session['condition']['walk']:
        suggest["suggest"]["type"] = "exercise"
        suggest["suggest"]["event"] = "walk"
        suggest["suggest"]["amount"] = abs(int((session['level']-session['condition']['walk'])/session['speed']['walk']))
        suggest["suggest"]["unit"] = "min"
    if session['level'] < session['condition']['rice']:
        suggest["suggest"]["type"] = "food"
        suggest["suggest"]["event"] = "rice"
        suggest["suggest"]["amount"] = 200
        suggest["suggest"]["unit"] = "g"
    elif session['level'] < session['condition']['banana']:
        suggest["suggest"]["type"] = "food"
        suggest["suggest"]["event"] = "banana"
        suggest["suggest"]["amount"] = 100
        suggest["suggest"]["unit"] = "g"
    elif session['level'] < session['condition']['chicken']:
        suggest["suggest"]["type"] = "food"
        suggest["suggest"]["event"] = "chicken"
        suggest["suggest"]["amount"] = 100
        suggest["suggest"]["unit"] = "g"
    if session['level'] >= session['condition']['chicken'] and session['level'] <= session['condition']['walk']:
        suggest["suggest"]["type"] = "good"

    return jsonify(suggest)


if __name__ == "__main__":
    app.secret_key = 'super secret key'
    app.config['SESSION_TYPE'] = 'filesystem'
    app.run(host="0.0.0.0", port=5000, debug=True)



