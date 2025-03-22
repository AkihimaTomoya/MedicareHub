// Khi load trang, ẩn modal
document.addEventListener('DOMContentLoaded', function() {
    const modal = document.getElementById('modal');
    if (modal) {
        modal.style.display = 'none';
    }
});

// Hiển thị modal với 3 form: login, register, forgot
function showForm(formType) {
    const modal = document.getElementById('modal');
    // Ẩn tất cả form trong modal
    document.querySelectorAll('.form-container').forEach(form => {
        form.style.display = 'none';
    });
    // Hiển thị modal
    modal.style.display = 'flex';
    // Hiển thị form tương ứng
    if (formType === 'login') {
        document.getElementById('login-form').style.display = 'block';
    } else if (formType === 'register') {
        document.getElementById('register-form').style.display = 'block';
    } else if (formType === 'forgot') {
        document.getElementById('forgot-form').style.display = 'block';
    }
}

// Ẩn modal
function hideForm() {
    const modal = document.getElementById('modal');
    if (modal) {
        modal.style.display = 'none';
    }
}

// Đóng modal khi click ra ngoài vùng nội dung modal
window.onclick = function(event) {
    const modal = document.getElementById('modal');
    if (modal && event.target === modal) {
        hideForm();
    }
};
document.getElementById("register-form-submit").addEventListener("submit", function(event) {
    event.preventDefault(); // Chặn hành vi submit mặc định

    const form = event.target;
    const formData = new FormData(form);

    fetch("auth/register", {
        method: "POST",
        body: formData
    })
        .then(async response => {
            const data = await response.json();
            if (!response.ok) {
                // Hiển thị lỗi trong form
                const errorDiv = document.getElementById("form-error");
                errorDiv.style.display = "block";
                errorDiv.innerText = data.error;
            } else {
                // Xử lý khi đăng ký thành công, ví dụ chuyển hướng hoặc thông báo
                alert(data.success);
            }
        })
        .catch(error => {
            console.error("Lỗi khi gửi dữ liệu:", error);
        });
});

// Smooth scroll function
function smoothScroll(event, sectionId) {
    const section = document.getElementById(sectionId);
    if (section) {
        section.scrollIntoView({ behavior: 'smooth' }); // Scroll smoothly to the section
    } else {
        console.warn(`Section ${sectionId} not found`);
    }
}


// // Xử lý submit form quên mật khẩu qua AJAX
// document.getElementById('forgot-form-submit').addEventListener('submit', function(event) {
//     event.preventDefault();
//     const form = event.target;
//     const formData = new FormData(form);
//     const jsonData = {};
//     for (const [key, value] of formData.entries()) {
//         jsonData[key] = value;
//     }
//     fetch(form.action, {
//         method: 'POST',
//         body: JSON.stringify(jsonData),
//         headers: { "Content-Type": "application/json" }
//     })
//         .then(response => response.text())
//         .then(text => {
//             alert(text);
//         })
//         .catch(error => {
//             console.error("Lỗi gửi yêu cầu quên mật khẩu:", error);
//             alert("Lỗi gửi yêu cầu");
//         });
// });
