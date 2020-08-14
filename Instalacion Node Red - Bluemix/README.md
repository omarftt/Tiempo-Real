
## Getting Started


### Installing dependencies

This file is provided by HRnet repository

```
pip install -r requirements.txt
```
	
### Files information

Here some additional information for new files added to HRnet repository:

```
${POSE_ROOT}
+-- data
     |-- mpii
	 |-- annot  : Contain the annotations for train, test, and validation
	 |-- images : Contain 25K images from MPII dataset, 3K diving images and 1K gym vault images. Also, here will be created the frames necessary for each video selected.
	 |-- Videos : Contain 370 diving videos.
	 |-- forTest
	       |-- centers: Contain 370 .mat files about the center feature for the 370 videos
	       |-- scales : Contain 370 .mat files about the scale  feature for the 370 videos
+-- experiments
+-- lib
+-- log
+-- models
+-- output
+-- tools 
    |-- ForVideo   : Contain the frames with the visualized joints for the video selected.	
    |-- Predictions: Contain the .npy files about the keypoints coordinates
    |-- train.py
    |-- test.py
    |-- visual.py: Tool created to visualize the human body joints in a selected frame.
+-- requirements.txt
```

