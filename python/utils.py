from urllib import request, parse


def send_request(from_group_id, chat_title, message_id_saved, content, is_private, username, msg_id):
    data = parse.urlencode([
        ("from_group_id", from_group_id),
        ("chat_title", chat_title),
        ("message_id_saved", message_id_saved),
        ("content", content),
        ("is_private", is_private),
        ("username", username),
        ("msg_id", msg_id)
    ])

    req = request.Request('http://127.0.0.1:8080/observer/observeServlet') 
    # 将127.0.0.1:8080更改为您的Tomcat访问路径

    with request.urlopen(req, data=data.encode("utf-8")) as f:
        if f.status == 200:
            return f.read().decode("utf-8")
        else:
            return 'Error:status code' + str(f.status)

# send_request("py", "2", "3", "5")
