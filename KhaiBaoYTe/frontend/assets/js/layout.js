/**
 * Inject sidebar + topbar cho các trang đã đăng nhập.
 * Cách dùng: gọi Layout.mount({ role:'citizen', active:'declare' })
 */
const NAV_CONFIG = {
  citizen: {
    title: 'Người dân',
    groups: [
      { label: 'Tổng quan', items: [
        { key:'dashboard', icon:'🏠', text:'Trang chủ',         href:'/frontend/pages/citizen/dashboard.html' },
      ]},
      { label: 'Khai báo y tế', items: [
        { key:'declare',   icon:'📝', text:'Khai báo mới',      href:'/frontend/pages/citizen/declare.html' },
        { key:'history',   icon:'📋', text:'Lịch sử khai báo',   href:'/frontend/pages/citizen/history.html' },
        { key:'health',    icon:'🌡️', text:'Cập nhật sức khoẻ', href:'/frontend/pages/citizen/health-update.html' },
      ]},
      { label: 'Hỗ trợ & Thông báo', items: [
        { key:'request',   icon:'🆘', text:'Yêu cầu hỗ trợ',     href:'/frontend/pages/citizen/requests.html' },
        { key:'notify',    icon:'🔔', text:'Thông báo',          href:'/frontend/pages/citizen/notifications.html' },
      ]},
      { label: 'Tài khoản', items: [
        { key:'profile',   icon:'👤', text:'Hồ sơ cá nhân',      href:'/frontend/pages/citizen/profile.html' },
      ]},
    ],
  },
  officer: {
    title: 'Cán bộ y tế',
    groups: [
      { label: 'Tổng quan', items: [
        { key:'dashboard', icon:'📊', text:'Bảng điều khiển',    href:'/frontend/pages/officer/dashboard.html' },
      ]},
      { label: 'Quản lý y tế', items: [
        { key:'declarations', icon:'📋', text:'DS khai báo',     href:'/frontend/pages/officer/declarations.html' },
        { key:'tracking',  icon:'❤️',  text:'Theo dõi sức khoẻ', href:'/frontend/pages/officer/tracking.html' },
        { key:'requests',  icon:'🆘',  text:'Yêu cầu hỗ trợ',    href:'/frontend/pages/officer/requests.html' },
      ]},
      { label: 'Truyền thông', items: [
        { key:'news',      icon:'📰', text:'Bài đăng tin tức',   href:'/frontend/pages/officer/news.html' },
        { key:'notify',    icon:'📣', text:'Gửi thông báo',      href:'/frontend/pages/officer/notifications.html' },
      ]},
      { label: 'Báo cáo', items: [
        { key:'reports',   icon:'📈', text:'Thống kê - báo cáo', href:'/frontend/pages/officer/reports.html' },
      ]},
    ],
  },
  admin: {
    title: 'Quản trị viên',
    groups: [
      { label: 'Tổng quan', items: [
        { key:'dashboard', icon:'🛡️', text:'Tổng quan hệ thống', href:'/frontend/pages/admin/dashboard.html' },
      ]},
      { label: 'Quản lý', items: [
        { key:'accounts',  icon:'👥', text:'Tài khoản',          href:'/frontend/pages/admin/accounts.html' },
        { key:'todanpho',  icon:'🏘️', text:'Tổ dân phố',         href:'/frontend/pages/admin/todanpho.html' },
        { key:'trangthai', icon:'🏷️', text:'Trạng thái sức khoẻ',href:'/frontend/pages/admin/trangthai.html' },
      ]},
    ],
  },
};

const Layout = {
  mount({ role, active, pageTitle, pageSub }){
    const user = Auth.requireRole(role);
    if(!user) return null;

    const cfg = NAV_CONFIG[role];
    const root = document.body;
    const app = document.createElement('div');
    app.className = 'app';

    /* ----- Sidebar ----- */
    const sidebar = document.createElement('aside');
    sidebar.className = 'sidebar';
    sidebar.innerHTML = `
      <div class="brand">
        <div class="logo">YT</div>
        <div>
          <div class="name">Khai báo Y tế</div>
          <div class="role">${cfg.title}</div>
        </div>
      </div>
      <nav class="nav">
        ${cfg.groups.map(g => `
          <div class="group-label">${g.label}</div>
          ${g.items.map(i => `
            <a href="${i.href}" data-key="${i.key}">
              <span class="ico">${i.icon}</span>${i.text}
            </a>`).join('')}
        `).join('')}
      </nav>
      <div class="foot">
        <div class="avatar">${(user.name||'?').charAt(0).toUpperCase()}</div>
        <div class="who">
          <div class="nm">${user.name}</div>
          <div class="rl">${cfg.title}</div>
        </div>
        <button title="Đăng xuất" id="btn-logout">⏻</button>
      </div>
    `;

    /* ----- Main ----- */
    const main = document.createElement('main');
    main.className = 'main';
    main.innerHTML = `
      <header class="topbar">
        <div class="flex items-center gap-3">
          <button class="menu-toggle" id="btn-menu">☰</button>
          <div class="page-title">${pageTitle || ''}</div>
        </div>
        <div class="topbar-right">
          <button class="icon-btn" title="Thông báo">🔔<span class="dot"></span></button>
          <button class="icon-btn" title="Trợ giúp">?</button>
        </div>
      </header>
      <section class="content" id="page-content"></section>
    `;

    app.appendChild(sidebar);
    app.appendChild(main);
    root.prepend(app);

    /* Đánh dấu menu active */
    if(active){
      const link = sidebar.querySelector(`.nav a[data-key="${active}"]`);
      link?.classList.add('active');
    }

    /* Sự kiện */
    sidebar.querySelector('#btn-logout').addEventListener('click', () => Auth.logout());
    main.querySelector('#btn-menu').addEventListener('click', () => sidebar.classList.toggle('open'));

    /* Header phụ trong content */
    const content = main.querySelector('#page-content');
    if(pageTitle){
      const h = document.createElement('div');
      h.className = 'content-header';
      h.innerHTML = `
        <div>
          <h1>${pageTitle}</h1>
          ${pageSub ? `<div class="sub">${pageSub}</div>` : ''}
        </div>
        <div class="content-actions"></div>
      `;
      content.appendChild(h);
    }
    return { user, content, headerActions: content.querySelector('.content-actions') };
  },
};
window.Layout = Layout;
