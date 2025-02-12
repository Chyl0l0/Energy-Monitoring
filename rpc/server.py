import json
import asyncio
import websockets
from jsonrpcserver import method, async_dispatch, dispatch, Success
import psycopg2
import datetime
import time
import os 
connection = psycopg2.connect(
    host=os.environ["DB_HOST"],
    dbname="city-db",
    user="root",
    password="root"
)
cursor = connection.cursor()

def create_table_if_not_exists():
   
    cursor.execute("""
    CREATE TABLE IF NOT EXISTS chat (
        ID  SERIAL PRIMARY KEY,
        sender BYTEA NOT NULL, 
        receiver BYTEA NOT NULL,
        message varchar(255) NOT NULL,
        time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        seen BOOLEAN NOT NULL DEFAULT FALSE
        )
    """)
    connection.commit()

   
def jsonify_messages(messages):
    messages_json = []
    for message in messages:
        messages_json.append({
            "sender": str(message[1], 'utf-8'),
            "receiver": str(message[2], 'utf-8'),
            "message": message[3],
            "time": datetime.datetime.timestamp(message[4]),
            "seen": str(message[5])
        })
    return messages_json

def create_result_rpc(messages):
    result = {
        "jsonrpc": "2.0",
        "id": 1,
        "result": messages
    }
    return str(result).replace("'", '"')

def create_notification_rpc(sender, receiver):
    result = {
        "jsonrpc": "2.0",
        "id": 1,
        "method": "typing_notification",
        "params": {
            "sender": sender,
            "receiver": receiver
        }
    }
    return str(result).replace("'", '"')


def insert_message(sender, receiver, message):
    cursor = connection.cursor()
    cursor.execute("""INSERT INTO chat (sender, receiver, message) VALUES (%s, %s, %s)""", (sender, receiver, message))
    connection.commit()

def get_messages(sender, receiver):
    cursor = connection.cursor()
    cursor.execute("""SELECT * FROM chat WHERE sender = %s AND receiver = %s OR sender = %s AND receiver = %s ORDER BY time""", (sender, receiver, receiver, sender))
    rows = cursor.fetchall()
    messages = jsonify_messages(rows)
    return messages

def mark_seen(sender, receiver):
    cursor = connection.cursor()
    cursor.execute("""UPDATE chat SET seen = TRUE WHERE sender = %s AND receiver = %s""", (sender, receiver))
    connection.commit()

# Register methods with the dispatcher
@method
async def send_message(sender: str, receiver: str, message: str):
    # code to send message
    insert_message(sender, receiver, message)
    print(connections.keys())
    if receiver in connections.keys():
        mark_seen(receiver, sender)
        mark_seen(sender, receiver)
        messages = get_messages(sender, receiver)
        print(receiver)
        await connections[receiver].send(create_result_rpc(messages))
        return Success(messages)
    print("not in")
    messages = get_messages(sender, receiver)
    return Success(messages)

@method
async def receive_messages(sender: str, receiver: str):
    # code to receive message
    mark_seen(receiver, sender)
    messages = get_messages(sender, receiver)
    if receiver in connections.keys():
        await connections[receiver].send(create_result_rpc(messages))
        
    return Success(messages)

@method
async def send_typing_notification(sender: str, receiver: str):
    # code to send typing notification
    if receiver in connections.keys():
        await connections[receiver].send(create_notification_rpc(sender, receiver))
    return Success("sent")
@method
async def login(user: str, password: str):
    # code to handle login
    return "logged in"

@method
async def logout(user: str):
    # code to handle logout
    return "logged out"


connections = dict()
# Create the WebSocket server
async def handle_connection(websocket, path):

    while True:
        try:
            message = await websocket.recv()
        except websockets.exceptions.ConnectionClosed:
            for key, value in connections.items():
                if value == websocket:
                    del connections[key]
                    break
            connections.pop(websocket)
            break
        # Parse the message as JSON
        data = json.loads(message)
        # Check if it's a JSON-RPC request
        if 'method' in data:
            request = str(data).replace("'", '"')
            connections[data["id"]] = websocket

            response = await async_dispatch(request)
            print(response)
            await websocket.send(response)
        else:
            # Handle other types of messages
            pass

start_server = websockets.serve(handle_connection, '0.0.0.0', 5678)

# Run the event loop

if __name__ == '__main__':
    print(os.environ["DB_HOST"])
    create_table_if_not_exists()
    asyncio.get_event_loop().run_until_complete(start_server)
    asyncio.get_event_loop().run_forever()
