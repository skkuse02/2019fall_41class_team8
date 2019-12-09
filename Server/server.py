import os
import flask
import werkzeug
from predict import *
from makeup.makeup import *

app = flask.Flask(__name__)

@app.route('/similar', methods = ['GET', 'POST'])
def similar_celebrities():
    imagefile = flask.request.files['image']
    filename = werkzeug.utils.secure_filename(imagefile.filename)
    imagefile.save('input\\' + filename)
    print("\nImage file is received.")

    celebrities = predict()
    print(celebrities)
    
    ret = ""
    for c in celebrities:
        ret += c[0] + '#' + str(c[1]) + '&'
    ret = ret[:-1]
    
    return ret

@app.route('/makeup/<args>', methods = ['GET'])
def madeup_image(args):
    celebName, celebPosition = args.split('&')
    
    print("Selected:", celebName, "-", celebPosition)

    makeup(celebName, celebPosition)

    print("Make up process complete.")
    
    return "Flask Server & Android are Working Successfully"


@app.route('/download', methods = ['GET', 'POST'])
def download_image():
    try:
        return flask.send_from_directory('output', 'output.jpg', as_attachment=True)
    except Exception as e:
        return str(e)

app.run(host="0.0.0.0", port=8000, debug=True)
