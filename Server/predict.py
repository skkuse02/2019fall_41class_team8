from face_aligner.align_faces import *

import torch
from torch import optim
import torchvision
import torch.nn as nn
import torchvision.datasets as dsets
import torchvision.utils as vutils
import torchvision.transforms as transforms
from torch.autograd import Variable

class VGG(nn.Module):
    def __init__(self):
        super(VGG, self).__init__()
        self.cnn = nn.Sequential(
            # 3 x 128 x 128
            nn.Conv2d(3, 64, kernel_size=3, padding=1),
            nn.ReLU(),
            nn.Conv2d(64, 64, kernel_size=3, padding=1),
            nn.ReLU(),
            nn.MaxPool2d(2, 2),
            # 64 x 64 x 64
            nn.Conv2d(64, 128, kernel_size=3, padding=1),
            nn.ReLU(),
            nn.Conv2d(128, 128, kernel_size=3, padding=1),
            nn.ReLU(),
            nn.MaxPool2d(2, 2),
            # 128 x 32 x 32
            nn.Conv2d(128, 256, kernel_size=3, padding=1),
            nn.ReLU(),
            nn.Conv2d(256, 256, kernel_size=3, padding=1),
            nn.ReLU(),
            nn.Conv2d(256, 256, kernel_size=3, padding=1),
            nn.ReLU(),
            nn.MaxPool2d(2, 2),
            # 256 x 16 x 16
            nn.Conv2d(256, 512, kernel_size=3, padding=1),
            nn.ReLU(),
            nn.Conv2d(512, 512, kernel_size=3, padding=1),
            nn.ReLU(),
            nn.Conv2d(512, 512, kernel_size=3, padding=1),
            nn.ReLU(),
            nn.MaxPool2d(2, 2),
            # 512 x 8 x 8
            nn.Conv2d(512, 512, kernel_size=3, padding=1),
            nn.ReLU(),
            nn.Conv2d(512, 512, kernel_size=3, padding=1),
            nn.ReLU(),
            nn.Conv2d(512, 512, kernel_size=3, padding=1),
            nn.ReLU(),
            nn.MaxPool2d(2, 2)
            # 512 x 4 x 4
        )
        self.fc = nn.Sequential(
            nn.Linear(512 * 4 * 4, 4096),
            nn.ReLU(),
            nn.Dropout(0.5),
                
            nn.Linear(4096, 2048),
            nn.ReLU(),
            nn.Dropout(0.5),
            
            nn.Linear(2048, 30)
        )
    def forward(self, x):
        output = self.cnn(x)
        output = output.view(output.size(0), -1)
        output = self.fc(output)
        return output
        
def weights_init(m):
    classname = m.__class__.__name__
    if classname.find('Conv') != -1:         # Conv weight init
        m.weight.data.normal_(0.0, 0.02)
    elif classname.find('BatchNorm') != -1:  # BatchNorm weight init
        m.weight.data.normal_(1.0, 0.02)
        m.bias.data.fill_(0)

def predict():
    align_faces()

    transform = transforms.Compose([
        transforms.ToTensor(),
        transforms.Normalize((0.5, 0.5, 0.5), (0.5, 0.5, 0.5))
    ])

    data = dsets.ImageFolder('./aligned_input', transform=transform)
    loader = torch.utils.data.DataLoader(data, batch_size=1, shuffle=True)

    model = VGG()
    model.apply(weights_init)
    model.cuda()
    model.load_state_dict(torch.load('vgg.pt'))
    model.eval()

    classes = ['chaeyeong', 'dahyeon', 'jeongyeon', 'jihyo', 'mina', 'momo', 'nayeon', 'sana', 'tzuyu']

    ret = []
    for images, labels in loader:
        images = Variable(images).cuda()
        outputs = model(images).cuda()
        #_, predicted = torch.max(outputs.cpu().data, 1)
        #print(classes[predicted])
        length = 4
        predicted = torch.topk(outputs.cpu().data, k = length, dim = 1)
        pv = predicted.values[0]
        pi = predicted.indices[0]
        for i in range(length):
            rate = int(pv[i]*10) // 2
            if rate > 100:
                rate = 100
            ret.append([classes[pi[i]], rate])

    return ret
    '''
    print('Test Accuracy of the model on the train images: %d %%' % (100 * correct / total))
    print("Correct: ", correct)
    '''
