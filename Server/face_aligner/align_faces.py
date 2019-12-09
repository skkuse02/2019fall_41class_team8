from imutils.face_utils import FaceAligner
from imutils.face_utils import rect_to_bb
import argparse 
import imutils
import dlib
import cv2
import os

def align_faces():
        detector = dlib.get_frontal_face_detector()
        predictor = dlib.shape_predictor("face_aligner\\shape_predictor_68_face_landmarks.dat")
        fa = FaceAligner(predictor, desiredFaceWidth=128)

        root_dir = 'input'
        des_dir = 'aligned_input\\0'
        if not os.path.exists(des_dir):
                os.mkdir(des_dir)

        input_files = [os.path.join(dp,f) for dp, dn, fn in os.walk(os.path.expanduser(root_dir)) for f in fn]

        # loop over the face detections
        for input_file in input_files:
                try:
                        image = cv2.imread(input_file)
                        image = imutils.resize(image, width=800)
                        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
                        des_path = '\\'.join(['.',des_dir] + input_file.split("\\")[2:-1])
                        des_list = des_path.split("\\")
                        for i in range(len(des_list)):
                                elem = des_list[:i+1]
                                if elem and not os.path.exists("\\".join(elem)):
                                        os.mkdir("\\".join(elem))
                        file_name = 'input.' + input_file.split(".")[-1]
                        out_file = os.path.join(des_path,file_name)
                        # show the original input image and detect faces in the grayscale
                        # image
                        rects = detector(gray, 2)
                        for rect in rects:
                                # extract the ROI of the *original* face, then align the face
                                # using facial landmarks
                                try:
                                        (x, y, w, h) = rect_to_bb(rect)
                                        faceOrig = imutils.resize(image[y:y + h, x:x + w], width=128)	
                                        faceAligned = fa.align(image, gray, rect)
                                        
                                        # display the output images
                                        if os.path.isfile(out_file):
                                                os.remove(out_file)
                                        cv2.imwrite(out_file,faceAligned)
                                        cv2.waitKey(0)
                                except:
                                        print("CANNOT SAVE")
                                        continue
                except:
                        pass
