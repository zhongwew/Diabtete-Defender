import pandas as pd
import datetime
import time
import math


class Solution:
    def __init__(self, df):
        self.df = df
        self.sleepRate = None


    def stampToSecs(self, stamp):
        d = datetime.datetime.strptime(stamp, "%Y/%m/%d %H:%M")
        t = d.timetuple()
        secs = int(time.mktime(t))
        return secs

    def calTimeGap(self, stamp1, stamp2):
        return math.fabs(self.stampToSecs(stamp1) - self.stampToSecs(stamp2))/60.0

    '''
    Func: Find the average glucose level of this time
    '''
    def findLevel(self, i):
        up = i
        down = i
        upValue = None
        downValue = None
        while up >= 0:
            if self.df.loc[up]['type'] == 'sugar':
                upValue = float(self.df.loc[up]['event'])
                break
            up -= 1
        while down < self.df.shape[0]:
            if self.df.loc[down]['type'] == 'sugar':
                downValue = float(self.df.loc[down]['event'])
                break
            down += 1
        downValue = upValue if downValue is None else downValue
        upValue = downValue if up is None else upValue
        return (upValue+downValue)/2


    def getRates(self, cur, type):
        pass

    def calSleepSpeed(self):
        i = 0
        sleepStart = None
        sleepEnd = None
        while i < self.df.shape[0]:
            cur = self.df.loc[i]
            type = cur['type']
            if type == 'sleep':
                if cur['status'] == 'begin':
                    sleepStart = (cur['time'], self.findLevel(i))
                if cur['status'] == 'end':
                    sleepEnd = (cur['time'], self.findLevel(i))
                if sleepStart and sleepEnd:
                    gap = self.calTimeGap(sleepEnd[0], sleepStart[0])
                    self.sleepRate = (sleepEnd[1]-sleepStart[1]) / gap
            i += 1
        return self.sleepRate


# df = pd.read_csv("data.csv")
# s = Solution(df)

# s.calSpeed()


