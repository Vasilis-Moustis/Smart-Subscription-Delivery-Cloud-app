# Smart-Sumbscription-Delivery-Cloud-app
A pair of a android and a django project
Android app: 
  supports clients and employs
  clients can create a new or view an existing subscription
  employees can create a smart path through the city and review the stock load for each delivery shift based on subs
  supports a login screen for employees
  connects to django db
 
Django app (AppSeed App Generator content used):
  handles connections from app
  returns data requested or stores recieved ones
  basic security implementations


How to start django: inside the project folder insert the following:
$ virtualenv env
$ source env/bin/activate
$ pip3 install -r requirements.txt
$ python manage.py makemigrations
$ python manage.py migrate
$ python manage.py runserver # default port 8000
$
$ # Start the app - custom port 
$ # python manage.py runserver 0.0.0.0:<your_port>
$
$ # Access the web app in browser: http://127.0.0.1:8000/

OR USE THE CUSTOM MADE startDjangoGlobal.sh (change ip settings in the file first)

FOR MORE INFO PLEASE VISIT: https://github.com/app-generator/flask-argon-dashboard
