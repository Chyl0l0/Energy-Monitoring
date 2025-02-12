import Cookies from 'js-cookie';
import React, { useState, useEffect } from 'react';
import './Chat.css';

 const Chat = () => {
    const [socket, setSocket] = useState(null);
    const [message, setMessage] = useState('');
    const [receiver, setReceiver] = useState('');
    const [seen, setSeen] = useState(false);
    const [typing, setTyping] = useState(false);

    const [messages, setMessages] = useState([]);

    useState(() => {
        if (Cookies.get('role') === 'admin') {
            setReceiver('03bbb7b8-3c08-43c4-a692-3c5acdd5ff65');
        }
        else {
            setReceiver('5bc8fd46-64bf-489b-8faa-0f02ed933695');
            
        }
    }, []);
    useEffect(() => {
        const ws = new WebSocket('ws://localhost:5678');
        setSocket(ws);
       
        
        ws.onopen = () => {
            console.log('WebSocket connected');
           
        };
        ws.onmessage = (event) => {
            const data = JSON.parse(event.data);

            if('method' in data){
                if(data.method === 'typing_notification'){
                    typing_notification(data.params.sender, data.params.receiver);
                }
            }
    
            if(data.result && data.result !== 'sent'){
                if(data.result.length > 0 ){
                    const msg = data.result[data.result.length - 1].message;

                    console.log(data.result[data.result.length - 1].seen)
                   
                    if(data.result[data.result.length - 1].seen === 'True'){
                        setSeen(true);
                    }
                    else{
                        setSeen(false);
                    }
                    setMessages(data.result);

                }
            }
        };
        ws.onclose = () => {
            console.log('WebSocket disconnected');
        };
    }, []);

    function typing_notification(sender, receiver) {
        if(Cookies.get('id') === receiver){
        console.log("typing");

            setTyping(true);
            setTimeout(() => {
                setTyping(false);
            }, 1000);
        }
    }
    const handleChange = (event) => {
        setMessage(event.target.value)
        event.preventDefault();
        if (socket) {
            socket.send(JSON.stringify({
                jsonrpc: "2.0",
                id: Cookies.get('id'),
                method: "send_typing_notification",
                params: {
                    sender: Cookies.get('id'),
                    receiver: receiver,
                }
            }));
        }
    }
    const handleConnect = (event) => {
        event.preventDefault();
        if (socket) {
            socket.send(JSON.stringify({
                jsonrpc: "2.0",
                id: Cookies.get('id'),
                method: "receive_messages",
                params: {
                    sender: Cookies.get('id'),
                    receiver: receiver,
                }
            }));
            console.log(receiver)

        }
        
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        if (socket) {
            socket.send(JSON.stringify({
                jsonrpc: "2.0",
                id: Cookies.get('id'),
                method: "send_message",
                params: {
                    sender: Cookies.get('id'),
                    receiver: receiver ,
                    message: message
                }
            }));
            setMessage('');
        }
    };

    return (
        
        <div className="chat-container">
            <form onSubmit={handleConnect}>
                <input
                    type="text"
                    className="chat-input"
                    value={receiver}
                    onChange={(event) => setReceiver(event.target.value)}
                />
                <button type="submit">Connect</button>

            </form>
            <div className="chat-history">
                {
                messages.map((message, index) => (
                    
                    <div key={index} className={`chat-message ${message.sender === Cookies.get('id') ? 'sender' : 'receiver'}`}>
                        <div className="chat-message-content">{message.message}</div>

                    </div>
                ))}
            </div>
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    className="chat-input"
                    value={message}
                    onChange={(event) => handleChange(event)}
                />
                <button type="submit">Send</button>
            </form>
            {
                seen ? <div>seen</div> : <div>not seen</div>
            }
            {
                typing ? <div>typing</div> : <div></div>
            }
        </div>
    );
};

export default Chat;
