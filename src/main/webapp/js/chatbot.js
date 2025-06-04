const chatbotToggle = document.getElementById('chatbot-toggle');
const chatbot = document.getElementById('chatbot');
const messages = document.getElementById('chatbot-messages');
const input = document.getElementById('chatbot-text');
const sendBtn = document.getElementById('chatbot-send');

// Danh sách câu hỏi - câu trả lời FAQ
const faq = {
    "giá vắc xin": "Giá vắc xin phụ thuộc vào từng loại, bạn có thể xem chi tiết tại trang danh mục vắc xin.",
    "đặt lịch tiêm": "Bạn có thể đặt lịch tiêm ngay trên website hoặc gọi hotline để được hỗ trợ.",
    "giờ làm việc": "Chúng tôi làm việc từ 8h sáng đến 5h chiều từ thứ 2 đến thứ 7.",
    "địa chỉ": "Trung tâm tiêm chủng nằm tại số 123 Đường ABC, Quận XYZ, TP. HCM.",
    "hỗ trợ": "Bạn cần hỗ trợ gì, vui lòng đặt câu hỏi hoặc gọi đến số hotline 0123 456 789."
};

// Mở/đóng chatbot
chatbotToggle.onclick = () => {
    if(chatbot.style.display === 'flex'){
        chatbot.style.display = 'none';
    } else {
        chatbot.style.display = 'flex';
        input.focus();

        if (messages.children.length === 0) {
            addMessage("Chào bạn! Tôi có thể giúp gì cho bạn hôm nay?", 'bot');
        }
    }
};

// Hàm hiển thị tin nhắn
function addMessage(text, sender){
    const msg = document.createElement('div');
    msg.className = 'message ' + sender;
    msg.textContent = text;
    messages.appendChild(msg);
    messages.scrollTop = messages.scrollHeight;
}

// Xử lý gửi câu hỏi
function sendQuestion(){
    const text = input.value.trim();
    if(!text) return;

    addMessage(text, 'user');
    input.value = '';

    // Tìm câu trả lời FAQ (so khớp đơn giản)
    let found = false;
    for(const key in faq){
        if(text.toLowerCase().includes(key)){
            addMessage(faq[key], 'bot');
            found = true;
            break;
        }
    }
    if(!found){
        addMessage("Xin lỗi, tôi chưa hiểu câu hỏi của bạn. Vui lòng thử lại hoặc liên hệ hotline.", 'bot');
    }
}

sendBtn.onclick = sendQuestion;
input.onkeydown = (e) => {
    if(e.key === 'Enter'){
        sendQuestion();
    }
}
