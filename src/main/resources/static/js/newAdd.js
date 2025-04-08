document.addEventListener("DOMContentLoaded", function () {
    const urlParams = new URLSearchParams(window.location.search);
    const addParam = urlParams.get("add");
    const editParam = urlParams.get("edit");

    const addModal = document.getElementById("add");
    const editModal = document.getElementById("edit");
    if (addParam === "true") {

        if (addModal) {
            addModal.style.display = "flex";
        }
    } else if (addParam === "false") {
        if (addModal) {
            addModal.style.display = "flex";
        }
    } else {
        hideForm()
    }

    if (editParam === "true") {
        history.replaceState(null, null, removeUrlParam("edit"));
        if (editModal) {
            editModal.style.display = "flex";
        }
    }else if (addParam === "false") {
        if (editModal) {
            editModal.style.display = "flex";
        }
    } else {
        hideForm()
    }
});

// Mở modal và cập nhật URL với ?add=true
function showForm(formtype) {
    if (formtype === 'add') {
        const modal = document.getElementById("add");
        if (modal) {
            modal.style.display = "flex";
            updateUrlParam("add", "true");
        }
    }
    if (formtype === 'edit') {
        const modal = document.getElementById("edit");
        if (modal) {
            modal.style.display = "flex";
            updateUrlParam("edit", "true");
        }
    }
    if (formtype === 'schedule') {
        const modal = document.getElementById("add");
        if (modal) {
            modal.style.display = "flex";
            updateUrlParam("add", "true");
        }
    }


}

// Đóng modal và xóa ?add=true khỏi URL
function hideForm() {
    const modal = document.getElementById("add");
    const edit = document.getElementById("edit");
    if (modal) {
        modal.style.display = "none";
        history.replaceState(null, null, removeUrlParam("add"));
    }
    if (edit) {
        edit.style.display = "none";
        history.replaceState(null, null, removeUrlParam("edit"));
    }
    const form = modal.querySelector("form");
    if (form) {
        form.reset();
        clearErrors();
    }
}

// Cập nhật tham số URL
function updateUrlParam(key, value) {
    const url = new URL(window.location);
    url.searchParams.set(key, value);
    window.history.pushState({}, "", url);
}

// Xóa tham số khỏi URL
function removeUrlParam(key) {
    const url = new URL(window.location);
    url.searchParams.delete(key);
    window.history.pushState({}, "", url);
}

// Xóa tất cả lỗi hiển thị
function clearErrors() {
    // Xóa nội dung thông báo lỗi và ẩn các phần tử có class error-message
    document.querySelectorAll(".error-message").forEach((el) => {
        el.innerHTML = "";
        el.style.display = "none";
    });
    // Nếu có thêm class đánh dấu lỗi trên input/textarea, xoá chúng
    document.querySelectorAll("#addForm input, #addForm textarea").forEach((el) => {
        el.classList.remove("error");
    });
}

// Đóng modal khi bấm ngoài vùng modal
window.onclick = function (event) {
    const modal = document.getElementById("add");
    if (modal && !modal.contains(event.target)) {
        hideForm();
    }
    const edit = document.getElementById("edit");
    if (edit && !edit.contains(event.target)) {
        hideForm();
    }
};

document.addEventListener("DOMContentLoaded", function () {
    const scheduleDateInput = document.getElementById("startDate");
    const scheduleEndDateInput = document.getElementById("endDate");
    const startDateContainer = document.getElementById("startDateContainer");
    const endDateContainer = document.getElementById("endDateContainer");
    const startDateSpan = document.getElementById("startDateSpan");
    const endDateSpan = document.getElementById("endDateSpan");

    let isEndDateEnabled = false; // Kiểm tra xem flatpickr của ngày kết thúc đã được khởi tạo hay chưa

    // Active span của ngày trực và hiển thị container tương ứng
    startDateSpan.classList.add("active");
    startDateContainer.style.display = "block";
    endDateContainer.style.display = "none";

    // Khởi tạo Flatpickr cho ngày trực
    flatpickr("#startDate", {
        inline: true,
        dateFormat: "Y-m-d",
        defaultDate: scheduleDateInput.value || new Date().toISOString().split("T")[0],
        onChange: function(selectedDates, dateStr) {
            scheduleDateInput.value = dateStr;
            // Nếu đã tạo flatpickr cho ngày kết thúc, cập nhật minDate theo ngày trực
            if (scheduleEndDateInput._flatpickr) {
                scheduleEndDateInput._flatpickr.set('minDate', dateStr);
            }
            // Khi chọn ngày trực lần đầu, kích hoạt ngày kết thúc
            if (!isEndDateEnabled) {
                isEndDateEnabled = true;
                // Chuyển active sang span "Ngày kết thúc"
                startDateSpan.classList.remove("active");
                endDateSpan.classList.add("active");
                // Ẩn flatpickr của ngày trực và hiển thị của ngày kết thúc
                startDateContainer.style.display = "none";
                endDateContainer.style.display = "block";
                // Khởi tạo Flatpickr cho ngày kết thúc nếu chưa có, với minDate = ngày trực
                if (!scheduleEndDateInput._flatpickr) {
                    flatpickr("#endDate", {
                        inline: true,
                        dateFormat: "Y-m-d",
                        defaultDate: dateStr,
                        minDate: dateStr,
                        onChange: function(selectedDates, dateStr) {
                            // Kiểm tra nếu ngày kết thúc nhỏ hơn ngày trực thì không cập nhật
                            const startDate = new Date(scheduleDateInput.value);
                            const endDate = new Date(dateStr);
                            if (endDate < startDate) {
                                // Nếu cần, có thể thông báo lỗi cho người dùng tại đây
                                scheduleEndDateInput.value = scheduleDateInput.value;
                            } else {
                                scheduleEndDateInput.value = dateStr;
                            }
                        }
                    });
                }
            }
        }
    });

    // Khi click vào "Ngày kết thúc": chỉ chuyển đổi nếu đã chọn ngày trực
    endDateSpan.addEventListener("click", function () {
        if (!isEndDateEnabled) return;
        startDateContainer.style.display = "none";
        endDateContainer.style.display = "block";
        startDateSpan.classList.remove("active");
        endDateSpan.classList.add("active");
    });

    // Khi click vào "Ngày trực": cho phép xem lại flatpickr của ngày trực
    startDateSpan.addEventListener("click", function () {
        if (!isEndDateEnabled) return;
        startDateContainer.style.display = "block";
        endDateContainer.style.display = "none";
        startDateSpan.classList.add("active");
        endDateSpan.classList.remove("active");
    });
});


function selectDoctor(element) {
    // Lấy dữ liệu từ data-attribute
    const doctorID = element.getAttribute("data-doctor-id");
    const doctorName = element.getAttribute("data-doctor-name");
    const specialty = element.getAttribute("data-specialty");

    // Điền vào form
    document.getElementById("doctorID").value = doctorID;
    document.getElementById("doctorName").value = doctorName;

    // Hiển thị form
    showForm('schedule');
}
