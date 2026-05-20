/**
 * Auth helper - dùng localStorage, mock dữ liệu để demo UI.
 * Khi nối với Spring Boot: thay phần login() bằng fetch('/api/auth/login').
 */
const AUTH_KEY = 'kby_user';

const ROLES = {
  GUEST: 'guest',
  CITIZEN: 'citizen',
  OFFICER: 'officer',
  ADMIN: 'admin',
};

/* Tài khoản mẫu */
const MOCK_USERS = [
  { id:'ND0001', username:'an',    password:'123', name:'Nguyễn Văn An',     role:ROLES.CITIZEN },
  { id:'ND0002', username:'binh',  password:'123', name:'Trần Thị Bình',      role:ROLES.CITIZEN },
  { id:'CB0001', username:'ha',    password:'123', name:'BS. Phạm Thu Hà',    role:ROLES.OFFICER },
  { id:'AD0001', username:'admin', password:'123', name:'Quản trị viên',      role:ROLES.ADMIN   },
];

const Auth = {
  ROLES,
  user(){ try { return JSON.parse(localStorage.getItem(AUTH_KEY)); } catch { return null; } },
  isLogged(){ return !!this.user(); },
  login(username, password){
    const u = MOCK_USERS.find(x => x.username === username && x.password === password);
    if(!u) return null;
    const data = { id:u.id, name:u.name, role:u.role, username:u.username };
    localStorage.setItem(AUTH_KEY, JSON.stringify(data));
    return data;
  },
  logout(){ localStorage.removeItem(AUTH_KEY); window.location.href = '/frontend/index.html'; },
  requireRole(role){
    const u = this.user();
    if(!u){ window.location.href = '/frontend/pages/auth/login.html'; return null; }
    if(role && u.role !== role){
      alert('Bạn không có quyền truy cập trang này.');
      window.location.href = '/frontend/index.html';
      return null;
    }
    return u;
  },
  homeFor(role){
    switch(role){
      case ROLES.CITIZEN: return '/frontend/pages/citizen/dashboard.html';
      case ROLES.OFFICER: return '/frontend/pages/officer/dashboard.html';
      case ROLES.ADMIN:   return '/frontend/pages/admin/dashboard.html';
      default:            return '/frontend/index.html';
    }
  }
};
window.Auth = Auth;
