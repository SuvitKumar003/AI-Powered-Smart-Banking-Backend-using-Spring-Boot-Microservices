import { Link, useLocation } from 'react-router-dom'
import { LayoutDashboard, CreditCard, PieChart, LogOut, Wallet } from 'lucide-react'
import '../styles/Navbar.css'

const Navbar = ({ onLogout }) => {
    const location = useLocation()

    return (
        <nav className="navbar glass-card">
            <div className="navbar-logo">
                <Wallet className="logo-icon" />
                <span>SmartBank<span className="ai-text">AI</span></span>
            </div>

            <div className="navbar-links">
                <Link to="/dashboard" className={location.pathname === '/dashboard' ? 'active' : ''}>
                    <LayoutDashboard size={20} />
                    <span>Dashboard</span>
                </Link>
                <Link to="/transactions" className={location.pathname === '/transactions' ? 'active' : ''}>
                    <CreditCard size={20} />
                    <span>Transactions</span>
                </Link>
                <Link to="/insights" className={location.pathname === '/insights' ? 'active' : ''}>
                    <PieChart size={20} />
                    <span>Insights</span>
                </Link>
            </div>

            <button className="logout-btn" onClick={onLogout}>
                <LogOut size={20} />
                <span>Logout</span>
            </button>
        </nav>
    )
}

export default Navbar
