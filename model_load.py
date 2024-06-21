import os
import tensorflow as tf

# Define the directory where the model is stored
model_dir = "model"

# Define the filename of the model
model_filename = "denseNet_model_final.h5"

# Construct the full path to the model file by joining the directory and filename
model_path = os.path.join(model_dir, model_filename)

# Load the TensorFlow Keras model from the specified file path
model = tf.keras.models.load_model(model_path)
