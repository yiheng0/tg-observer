from telethon import TelegramClient, events
from telethon.tl.functions.account import UpdateStatusRequest
from telethon.tl.functions.messages import ForwardMessagesRequest
from telethon.tl.types import *
from data import *
from utils import send_request

client = TelegramClient('session_file', api_id, api_hash, update_workers=1, spawn_read_thread=False)
client.start()

client(UpdateStatusRequest(offline=True))

destination = PeerChannel(fwd_channel[0])


@client.on(events.NewMessage(incoming=True))
def my_event_handler(event):
    if event.input_sender.user_id in listened_persons_id:

        fwd_msg = client(ForwardMessagesRequest(
            from_peer=event.message.to_id,  # who sent these messages?
            id=[event.message.id],  # which are the messages?
            to_peer=destination  # who are we forwarding them to?
        ))

        chat = client.get_entity(event.message.to_id)
        msg_id = event.message.id
        print("title:", chat.title)
        print("chat_id:", chat.id)
        print("msg_id", msg_id)
        print("saved_id:", fwd_msg.updates[0].id)
        print("content:", event.raw_text)
        print("type:", type(chat))
        is_private = True
        if isinstance(chat, Channel):
            print("username:", chat.username)
            is_private = False
        #
        if is_private:
            result = send_request(str(chat.id), str(chat.title), str(fwd_msg.updates[0].id), str(event.raw_text),
                                  True, "", str(msg_id))
        else:
            result = send_request(str(chat.id), str(chat.title), str(fwd_msg.updates[0].id), str(event.raw_text),
                                  False, chat.username, str(msg_id))
        print(result)
        print("--------------------------------------------------------")


print('Started')
client.idle()
