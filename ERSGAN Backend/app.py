# Python Flask API for serving the PyTorch model
import io
import sys
import cv2
import numpy as np
import time

from flask import Flask, request, jsonify, send_file
import torch
from torchvision.transforms import ToTensor, ToPILImage
from PIL import Image
from ESRGAN import esrgan
app = Flask(__name__)
# def convert_cv2_to_bytes(cv2_image):
#     # Encode the image to JPEG format
#     _, image_buffer = cv2.imencode('.jpg', cv2_image)

#     # Convert the image buffer to a byte array
#     byte_array = np.array(image_buffer).tobytes()

#     return byte_array

@app.route('/upscale', methods=['POST'])
def upscale_image():
    try:
        
        # Get the input image from the request
        image_file = request.files['image']
        print(image_file)
        # Convert FileStorage to PIL Image
        pil_image = Image.open(image_file)

        # Convert PIL Image to cv2 image (numpy array)
        cv2_image = cv2.cvtColor(np.array(pil_image), cv2.COLOR_RGB2BGR)

        img = esrgan.upscale(cv2_image)
        # # Convert cv2 image to byte array
        _, buffer = cv2.imencode('.jpg', img)
        byte_array = buffer.tobytes()

        # Example: Save the byte array as an image (for testing purposes)
        with open('output.jpg', 'wb') as output_file:
            output_file.write(byte_array)

        # Return the byte array as a file
        return send_file(io.BytesIO(byte_array), mimetype='image/jpeg', as_attachment=True, download_name='output.jpg')
        # return send_file(byte_array, mimetype='image/jpeg', as_attachment=True, download_name='upscaled_image.jpeg')

    except Exception as e:
        return jsonify({"result": "error", "message": str(e)})

if __name__ == '__main__':
    app.run(port=5000)
