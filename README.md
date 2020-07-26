# text-to-image - A Java Swing software to convert text to images

This application is for creating images from text. Suitable for posting poetry, text, quotes on Instagram, Facebook etc.

Input:
1. "Source File" (mandatory) is a comma seperated file. Each line in image is comma seperated.
    1. Each row in file creates one image
    1. Two languages are supported, English and Urdu.
    1. First value in each row should be the language name, either "English" or "Urdu".
1. "Background Image" (optional) is the image to be used as background, ideal size is 1080 x 1080
1. "Header Image" (optional) is an image which will be added on top left of your output image file.
1. "Footeer Image" is an image which will be added on bottom left of your output image file

![Application Preview](/media/AppSnapshot.png)

Output:
Images with data from "Source File".