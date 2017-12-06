import os
import requests
import json
import datetime
import time
from requests.auth import HTTPBasicAuth
import rasterio
import numpy
from xml.dom import minidom
import matplotlib.pyplot as plt
from PIL import Image
from flask import Flask, request, send_file

#The server program and NDVI funtion are at the bottom

# our demo filter that filters by geometry, date and cloud cover. I used Planet.com's as a template and altered it.
from demo_filters import all_filters

#my API key
os.environ['PL_API_KEY'] = "9130cf0c4fdf4ea099a172ab08c84525" # my planet API key

asset_url1 = None
asset_url2= None
check_dict1=None
check_dict2 = None
asset_ID= None
asset_ID1=None
asset_ID2=None


#The following two functions alter the filters loaded in all_filters

#sets coordinates filter. Arguments are expected to be strings
#def set_coordinates(a1, a2, b1, b2, c1, c2, d1, d2):
#	coor_array = all_filters["config"][0]['config']['coordinates'][0]#['coordinates'][0][0]
#	coor_array[0][0] =a1
#	coor_array[0][1] =a2
#	coor_array[1][0] =b1
#	coor_array[1][1] =b2
#	coor_array[2][0] =c1
#	coor_array[2][1] =c2
#	coor_array[3][0] =d1
#	coor_array[3][1] =d2
#	coor_array[4][0] =a1
#	coor_array[4][1] =a2


#Here the program will receive input from the android user. Coordinates and dates will be applied
#to the json filters using the functions above. For now I rely on dummy ones.
def set_coordinates(lati, longi):
	coor_array = all_filters["config"][0]['config']['coordinates'][0]
	lati = float(lati) #converting from string
	longi = float(longi)
	lat_shift = 0.07
	long_shift = 0.07

	coor_array[0][0] =lati -lat_shift
	coor_array[0][1] =longi -long_shift
	coor_array[1][0] =lati+ lat_shift
	coor_array[1][1] =longi - long_shift
	coor_array[2][0] =lati+lat_shift
	coor_array[2][1] =longi + long_shift
	coor_array[3][0] =lati-lat_shift
	coor_array[3][1] =longi + long_shift
	coor_array[4][0] =lati - lat_shift
	coor_array[4][1] =longi - long_shift






def set_date (y, m, d): #arguments expected to be Strings. I have commented out the first part, which is a range search, when I realized that before
#2017, dates with imagery are fairly scarce. In 2017, there is an image at least every couple days, which is the assumption that the commented code makes.

	"""
	y=int(y)
	m=int(m)
	d=int(d)
	to_append = "T00:00:00.000Z"
	start_date = datetime.datetime(y, m, d) #year, month, day
	for i in range(0, 3):
		start_date -= datetime.timedelta(days=1)
	start_date_string = str(start_date)
	start_date_string = start_date_string[:-9]
	start_date_string+=to_append
	#print start_date_string

	end_date = datetime.datetime(y, m, d) #year, month, day
	for i in range(0, 3):
		end_date += datetime.timedelta(days=1)
	end_date_string = str(end_date)
	end_date_string = end_date_string[:-9]
	end_date_string+=to_append
	
	all_filters['config'][1]["config"]["gte"] = start_date_string
	all_filters['config'][1]["config"]["lte"] = end_date_string
	"""
	to_append = "T00:00:00.000Z"
	start_date_string = y +"-"+m+"-"+d+to_append
	d_int = int(d)
	d_int+=1
	d = str(d_int)
	end_date_string = y +"-"+m+"-"+d+to_append
	all_filters['config'][1]["config"]["gte"] = start_date_string
	all_filters['config'][1]["config"]["lte"] = end_date_string

# Search API request object
search_endpoint_request = {
  "item_types": ["PSOrthoTile"], #WILL BE CHANGED to planetscape 4-band
  "filter": all_filters

}

def submit_request(reqNo):
	global asset_url1
	global asset_url2
	global asset_ID
	global asset_ID1
	global asset_ID2

	#submitting filtered request to Planet endpoint
	result = \
  	requests.post(
    	'https://api.planet.com/data/v1/quick-search',
    	auth=HTTPBasicAuth(os.environ['PL_API_KEY'], ''),
    	json=search_endpoint_request)
  	#print result

	#now to extract the link to the first asset listed
	response_dict = json.loads(result.text)
	print json.dumps(response_dict, indent=1)
	#print response_dict
	#asset_url = None
	if reqNo == 1:
		print "in reqNo 1"
		asset_url1 = response_dict['features'][0]['_links']['assets'] #this is the link to the asset
		print "asset_url1 " +asset_url1 
		asset_ID1= response_dict['features'][0]['id']
		item = requests.get(asset_url1, auth=HTTPBasicAuth(os.environ['PL_API_KEY'], ''))
		#asset_url = asset_url1
	elif reqNo == 2:
		asset_url2 = response_dict['features'][0]['_links']['assets'] #this is the link to the asset
		asset_ID2= response_dict['features'][0]['id']
		item = requests.get(asset_url2, auth=HTTPBasicAuth(os.environ['PL_API_KEY'], ''))
		#asset_url = asset_url2
	#print asset_url
	#item = requests.get(asset_url, auth=HTTPBasicAuth(os.environ['PL_API_KEY'], ''))
	item_type = "PSOrthoTile" #ONLY FOR TESTING. WILL BE PLANETSCAPE ANALYTIC. THIS SHOULD ALSO BE MANUALLY CHANGED IN THE FILTER FILE
	asset_type = "analytic" #ONLY FOR TESTING. WILL BE PLANETSCAPE ANALYTIC 
	asset_type_xml = "analytic_xml"
	item_activation_url = item.json()[asset_type]["_links"]["activate"]
	item_activation_url_xml = item.json()[asset_type_xml]["_links"]["activate"]

	print item_activation_url
	response = requests.post(item_activation_url, auth=HTTPBasicAuth(os.environ['PL_API_KEY'], '')) #posting request that image be prepared for download
	response_xml = requests.post(item_activation_url_xml, auth=HTTPBasicAuth(os.environ['PL_API_KEY'], ''))

def download(reqNo):
	#global asset_ID
	print "entering download"
	if reqNo == 1:
		#asset_ID = asset_ID1; 
		download_url = check_dict1['analytic']['location']
		download_url_xml = check_dict1['analytic_xml']['location']
		r = requests.get(download_url, auth=HTTPBasicAuth(os.environ['PL_API_KEY'], ''), stream = True)
		r_xml = requests.get(download_url_xml, auth=HTTPBasicAuth(os.environ['PL_API_KEY'], ''), stream = True)

		with open(asset_ID1 + ".xml","wb") as xml:
			for chunk in r_xml.iter_content(chunk_size=1024):
				if chunk:
					xml.write(chunk)

		print "completed xml download"
	
		#with open(asset_ID + ".tiff","wb") as tiff:
		#	for chunk in r.iter_content(chunk_size=1024):
		#		if chunk:
		#			tiff.write(chunk)
		with open(asset_ID1 + ".tiff","wb") as tiff:
			for chunk in r.iter_content(1024):
				tiff.write(chunk)

        print "completed image download"

	#elif reqNo==2:
	 #   asset_ID = asset_ID2;
	

#Compare takes the coordinates and both days, and then requests both asset resources at the same time, to reduce retrieval time by 1/2. Still about ten minutes.
def compare(lati, longi, y1, m1, d1, y2, m2, d2):
	global check_dict1
	global check_dict2
	print "in compare"
	set_coordinates(lati, longi)
	set_date(y1, m1, d1)
	print "before submit_request"
	submit_request(1) #submitted request for first image
	print "after submit_request"

	

	while True: #waits until both resources available before downloading (should be available at roughly same time, since requested same time)
		print "Planet servers can take between eight and twenty minutes to retrieve assets"
		check1 = requests.get(asset_url1, auth=HTTPBasicAuth(os.environ['PL_API_KEY'], ''))
		check_dict1 = json.loads(check1.text)
		#check2 = requests.get(asset_url2, auth=HTTPBasicAuth(os.environ['PL_API_KEY'], ''))
		#check_dict2 = json.loads(check2.text)
		status1 = check_dict1['analytic']['status'] 
		status1_xml = check_dict1['analytic_xml']['status'] 
		#status2 = check_dict2['analytic']['status'] 
		#status2_xml = check_dict2['analytic_xml']['status'] 
		if status1 == "active" and status1_xml=="active":
			# && status2 == "active" && status2_xml =="active":
			print "BOTH ASSETS ARE NOW ACTIVE"
			break
		else:
			time.sleep(30) #image may take around ten-twenty minutes to prepare. 
	print "begin download"
	download(1)
	print "finish download"
	




# The following code is (significantly!) adapted from the Planet's NDVI tutorial:
def ndvi():
	image_file = asset_ID1 +".tiff"

# Load red and NIR bands - note all PlanetScope 4-band images have band order BGRN
	with rasterio.open(image_file) as src:
		band_red = src.read(3)

	with rasterio.open(image_file) as src:
		band_nir = src.read(4)
		

#xmldoc = minidom.parse("501518_1056620_2017-05-13_0e0d_BGRN_Analytic_metadata.xml")
	xmldoc = minidom.parse(asset_ID1+".xml")

	nodes = xmldoc.getElementsByTagName("ps:bandSpecificMetadata")

# XML parser refers to bands by numbers 1-4
	coeffs = {}
	for node in nodes:
		bn = node.getElementsByTagName("ps:bandNumber")[0].firstChild.data
		if bn in ['1', '2', '3', '4']:
			i = int(bn)
			value = node.getElementsByTagName("ps:reflectanceCoefficient")[0].firstChild.data
			coeffs[i] = float(value)

# Multiply by corresponding coefficients
	band_red = band_red * coeffs[3]
	band_nir = band_nir * coeffs[4]


# Allow division by zero
	numpy.seterr(divide='ignore', invalid='ignore')

# Calculate NDVI
	ndvi = (band_nir.astype(float) - band_red.astype(float)) / (band_nir + band_red)

# Set spatial characteristics of the output object to mirror the input
	kwargs = src.meta
	kwargs.update(
		dtype=rasterio.float32,
		count = 1)

# Create the file
	with rasterio.open(asset_ID1+'.tiff', 'w', **kwargs) as dst:
		dst.write_band(1, ndvi.astype(rasterio.float32))


	
	plt.imsave(asset_ID1+".png", ndvi, cmap=plt.cm.YlGn) 
#plt.imsave("ndvi_cmap2.png", ndvi, cmap=plt.cm.YlGn)

	
	im = Image.open(asset_ID1+".png")
	im.convert('RGB').save(asset_ID1+".jpg","JPEG")




app = Flask(__name__)
@app.route('/', methods=['POST'])
def result():
	#global asset_ID
	print "received POST request"
	requestType = request.form['requestType'] 
	if requestType == "sendThumbnail": #simple case designed to show Android-end developers how to use the protocol, receive images
		print "received requestType " + requestType
		return send_file("thumb.png", mimetype='image/png')
	elif requestType == "compareRequest": #return two images on different dates 
		print "received requestType " + requestType
		#If we got to the point of Android user input, these inputs would be sent as
		# key-value pairs from the Android client. For now just the requestType is included in the request from Android, and the coordinates
		#and dates are hard-coded
		compare("-121.5344519", "38.04", "2017", "06", "24", "2017", "07", "13") 
		print "reached sending back to java"
		ndvi()
		return send_file(asset_ID1+".jpg", mimetype='image/jpeg') #returns compressed, NDVI'd image to requesting client