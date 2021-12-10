#!/usr/bin/python3.6
import time
import hmac
import hashlib
import base64
import urllib.parse
import io
import requests, json  # 导入依赖库
import sys

class DingDingHandler:
    def __init__(self, token, secret):
        self.token = token
        self.secret = secret

    def get_url(self):
        timestamp = round(time.time() * 1000)
        secret_enc = self.secret.encode("utf-8")
        string_to_sign = "{}\n{}".format(timestamp, self.secret)
        string_to_sign_enc = string_to_sign.encode("utf-8")
        hmac_code = hmac.new(
            secret_enc, string_to_sign_enc, digestmod=hashlib.sha256
        ).digest()
        sign = urllib.parse.quote_plus(base64.b64encode(hmac_code))
        api_url = "https://oapi.dingtalk.com/robot/send?access_token={}&timestamp={}&sign={}".format(
            self.token, timestamp, sign
        )
        return api_url

    def ddlinksend(self, link, text, title):
        headers = {"Content-Type": "application/json"}  # 定义数据类型
        data = {
            "msgtype": "link",
            "link": {
                "text": text,
                "title": title, 
                "messageUrl": link,
            },
        }
        res = requests.post(self.get_url(), data=json.dumps(data), headers=headers)  # 发送post请求
        print(res.text)

    def ddtextsend(self, m ,n):
        headers = {"Content-Type": "application/json"}  # 定义数据类型
        data = {
            "msgtype": "text",
            "text": {

                "content":'注意: java进程当前CPU占比: ' + m + '%' + ' MEM占比: ' + n + '%'
            },
        }
        res = requests.post(self.get_url(), data=json.dumps(data), headers=headers)  # 发送post请求
        print(res.text)

a = sys.argv[1]    #获取java进程的整行信息
li = a.split()	   #以空格为分隔符，将每一列存入到字符数组中
m=li[8]		   #取出CPU的值
b=(float)(m)       #强制转换
n=li[9]
if b > 20 :
    token="1e097e490abab9bdc1d3ed0a3470ed407be685466842e6750bd7e1145d8cad75"
    secret="SECb3dd578b1dfbb10a2fd993c1a1b668278ed6f6df848869a3960fe3c09a54e032"
    dingDingHandler =DingDingHandler(token,secret)
    dingDingHandler.ddtextsend(m,n)
else:
    print("当前java进程CPU占比低于20%")
