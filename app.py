# app.py

import os
from flask import Flask, request, jsonify
from flask_cors import CORS
import numpy as np
from PIL import Image
from tensorflow.keras.preprocessing import image
from model_load import model
from class_list import class_list  # Import class_list from class_list.py

# Set the environment variable to disable oneDNN optimizations
os.environ['TF_ENABLE_ONEDNN_OPTS'] = '0'

app = Flask(__name__)
CORS(app)

# Function to preprocess the image
def preprocess_image(img):
    img = img.resize((416, 416))
    x = image.img_to_array(img)
    x = np.expand_dims(x, axis=0)
    x = x / 255.0
    return x

# Function to make predictions
def predict_image(model, img):
    x = preprocess_image(img)
    predictions = model.predict(x)
    predicted_class_index = np.argmax(predictions)
    predicted_class = class_list[predicted_class_index]
    return predicted_class, predictions[0]

@app.route('/predict', methods=['POST'])
def predict():
    try:
        if 'image' not in request.files:
            return jsonify({'error': 'Image data not found'}), 400

        image_data = request.files['image']
        img = Image.open(image_data)
        img = img.convert('RGB')

        predicted_class, predictions = predict_image(model, img)
        confidence = float(np.max(predictions))

        return jsonify({'class': predicted_class, 'confidence': round(confidence, 2)}), 200

    except Exception as e:
        return jsonify({'error': str(e)}), 400

# Add new route to get all class list
@app.route('/', methods=['GET'])
def get_class_list():
    try:
        return jsonify({'class_list': class_list}), 200
    except Exception as e:
        return jsonify({'error': str(e)}), 500

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=3000, debug=True)
