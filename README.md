# CSC207 Project

# Innovative Air Quality Map Application

## Problem Domain
Our team specializes in the field of environment and health, especially air quality. With increasing levels of air pollution in many cities, there is a growing need for real-time air quality monitoring to protect public health and raise awareness of environmental issues.

## Application Overview
My team is contemplating the development of an innovative Air Quality Map Application, designed to provide real-time, accurate data on air quality levels across various locations globally. Utilizing a user-friendly interface, individuals can easily view and analyze air pollution metrics in their vicinity, empowering them to make informed decisions for outdoor activities and health precautions. Integrated with various reputable air quality data sources, the application ensures reliability and comprehensiveness in its readings.

## API Documentation
We are using the IQAir API to fetch the air quality data. The API documentation can be found [here](https://api-docs.iqair.com/).

![API Test Screenshot](<link-to-your-screenshot>)  
*Above is a screenshot of testing the IQAir API using Postman.*

## Example Output
```plaintext
{
    "status": "success",
    "data": {
        "city": "Ashburn",
        "state": "Virginia",
        "country": "USA",
        "location": {
            "type": "Point",
            "coordinates": [
                -77.489998,
                39.024399
            ]
        },
        "current": {
            "pollution": {
                "ts": "2023-09-28T23:00:00.000Z",
                "aqius": 88,
                "mainus": "o3",
                "aqicn": 107,
                "maincn": "o3"
            },
            "weather": {
                "ts": "2023-09-28T22:00:00.000Z",
                "tp": 19,
                "pr": 1019,
                "hu": 73,
                "ws": 2.57,
                "wd": 70,
                "ic": "04d"
            }
        }
    }
    
}
```
## Technical Problems
We are currently facing a few technical challenges:

1. **Finding a Free API:**
    - We have had difficulty locating a suitable and freely available API online that meets our specific needs for air quality data.

2. **API Testing:**
    - We are experiencing issues with running the chosen API in our testing tool, which is impeding our ability to validate the API’s functionality and data accuracy.

3. **Git Operations:**
    - At the initial stage of setting up our project repository on GitHub, we encountered problems with pulling the repository, which has affected our project setup and progress.

We are actively seeking solutions to these issues and utilizing resources such as Piazza for additional support and guidance.
