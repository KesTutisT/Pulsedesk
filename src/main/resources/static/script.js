const ticketList = document.getElementById('ticketList');
const commentList = document.getElementById('commentList');
const statusMsg = document.getElementById('statusMessage');

// Category color map
const categoryColors = {
    bug:     { bg: '#fee2e2', text: '#dc2626' },
    billing: { bg: '#14ba26', text: '#042909' },
    feature: { bg: '#dbeafe', text: '#2563eb' },
    account: { bg: '#fef9c3', text: '#ca8a04' },
    other:   { bg: '#f3f4f6', text: '#6b7280' },
};

// Priority color map
const priorityColors = {
    high:   '#dc2626',
    medium: '#d97706',
    low:    '#16a34a',
};

// Fetch and Refresh everything
async function refreshDashboard() {
    try {
        // Fetch Comments
        const commentRes = await fetch('/comments');
        const comments = await commentRes.json();

        // Fetch Tickets
        const ticketRes = await fetch('/tickets');
        const tickets = await ticketRes.json();

        renderLists(comments, tickets);
    } catch (err) {
        console.error("Failed to fetch data", err);
    }
}

function renderLists(comments, tickets) {
    commentList.innerHTML = "";
    ticketList.innerHTML = "";

    document.getElementById('commentCount').innerText = comments.length;
    document.getElementById('ticketCount').innerText = tickets.length;

    comments.reverse().forEach(c => {
        const div = document.createElement('div');
        div.className = 'item-card';
        div.innerHTML = `<strong>${c.author}</strong>: ${c.text}`;
        commentList.appendChild(div);
    });

    tickets.reverse().forEach(t => {
        const categoryKey = t.category.toLowerCase();
        const priorityKey = t.priority.toLowerCase();

        const catColor = categoryColors[categoryKey] || categoryColors.other;
        const priColor = priorityColors[priorityKey] || '#6b7280';

        const cleanTitle = t.title.replace(/^\[.*?\]\s*/g, '').trim();

        const cleanTitle2 = cleanTitle.replace(/^\[.*?\]\s*/g, '').trim();

        const div = document.createElement('div');
        div.className = 'item-card ticket-card';
        div.innerHTML = `
            <div style="display:flex; align-items:center; gap:6px; margin-bottom:6px; flex-wrap:wrap;">
                <span style="
                    background:${catColor.bg};
                    color:${catColor.text};
                    font-weight:700;
                    font-size:11px;
                    padding:2px 6px;
                    border-radius:4px;
                ">[${t.category.toUpperCase()}]</span>

                <span style="
                    background:#f3f4f6;
                    color:${priColor};
                    font-weight:700;
                    font-size:11px;
                    padding:2px 6px;
                    border-radius:4px;
                ">[${t.priority.toUpperCase()}]</span>

                <span style="font-weight:600; font-size:14px;">
                    ${cleanTitle2}
                </span>
            </div>

            <!-- LINE 2: Body -->
            <div style="font-size:14px; margin-bottom:6px;">
                ${t.text || ''}
            </div>

            <!-- LINE 3: Author -->
            <div style="font-size:12px; color:#6b7280;">
                Author: ${t.author}
            </div>
        `;
        ticketList.appendChild(div);
    });
}

// Submit new comment
document.getElementById('submitBtn').addEventListener('click', async () => {
    const author = document.getElementById('author').value;
    const text = document.getElementById('text').value;

    if (!author || !text) return;

    statusMsg.classList.remove('hidden');
    statusMsg.innerText = "AI is thinking...";

    await fetch('/comments', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ author, text })
    });

    document.getElementById('text').value = "";
    statusMsg.innerText = "Done!";

    // Auto-refresh after a small delay to allow AI to finish
    setTimeout(refreshDashboard, 1500);
});

document.getElementById('refreshBtn').addEventListener('click', refreshDashboard);

refreshDashboard();