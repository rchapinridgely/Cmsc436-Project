This is the backend for the Planet App! It uses a Flask server to receive requests for
satellite imagery from the Planet.com. The server checks the key-value pairs sent
to it from the Android app, starting with the requestType. The app is incomplete, so 
currently the other parameters (date and coordinates of image) that would come from
Android are hard-coded. The android-end did accomplish geocoding, which is the hard part of getting these coordinates, but it is not yet tied to the backend. Only the requestType comes from POST at the moment. 

So, after receiving the date and coordinates, the app searches the Planet.com API
endpoint for matching images. This mainly involves parsing JSON. It then requesets the images and their corresponding .xml file to activated. Planet.com's database is not instant. It tends to take around ten minutes for these resources to become available. Meanwhile, the backend checks on their status every 30 seconds, and begins downloading once it receives the OK. The Analytic data we're interesting is not viewable (yet)
by humans. It's broad-spectrum, and we're only interested in two frequences for our
NDVI analysis: near-infra-red, and red. The program, once the files have downloaded, extracts those two bands, and runs an analyisis on them, plotting the reflectivity of
each band in a range of [-1 to 1], and mapping that onto a spectral, human-visible 
map that ranges from yellow, which indicates low NDVI, and low vegitation, to green, 
which is the opposite. So the program outputs that PNG file in the folder from which you run it, and then takes the additional step of compressing to JPG for the sake of 
small file size and easy transmission to the Android App. 

The backend, by the way, is found mainly in the file backend.py, which also uses 
the demo_filters.py file to run. 

Yes, although incomplete, the backend functions well. I say that because it will be
a pain to install. There are many dependencies, which are listed as imports. Not all of
them are standard. The ones that may require explicit installation are:

requests pip install requests
retrying pip install retrying
Rasterio - pip install rasterio
Numpy 
matplotlib - pip install matplotlib
Pillow https://pillow.readthedocs.io/en/4.3.x/

and it runs in a virtual environment. Here are the instructions for that and Flask:
http://flask.pocoo.org/docs/0.12/installation/

Included are three Java files. Two of them can be run in Android (MainActivity and 
RequestTask), and the other, Task.java, can be run on a normal computer to see that
the backend sends data back to the client. 
