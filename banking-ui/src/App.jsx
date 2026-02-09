import { useState, useEffect } from 'react'
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom'
import Navbar from './components/Navbar'
import Dashboard from './components/Dashboard'
import Login from './components/Login'
import Register from './components/Register'
import Transactions from './components/Transactions'
import Insights from './components/Insights'
import './App.css'

function App() {
    const [user, setUser] = useState(null)
    const [token, setToken] = useState(localStorage.getItem('token'))

    useEffect(() => {
        if (token) {
            localStorage.setItem('token', token)
            // We could optionally fetch user profile here
        } else {
            localStorage.removeItem('token')
        }
    }, [token])

    const logout = () => {
        setToken(null)
        setUser(null)
        localStorage.removeItem('token')
    }

    return (
        <Router>
            <div className="app-container">
                {token && <Navbar user={user} onLogout={logout} />}
                <main className="main-content">
                    <Routes>
                        <Route
                            path="/login"
                            element={!token ? <Login setToken={setToken} setUser={setUser} /> : <Navigate to="/dashboard" />}
                        />
                        <Route
                            path="/register"
                            element={!token ? <Register /> : <Navigate to="/dashboard" />}
                        />
                        <Route
                            path="/dashboard"
                            element={token ? <Dashboard token={token} /> : <Navigate to="/login" />}
                        />
                        <Route
                            path="/transactions"
                            element={token ? <Transactions token={token} /> : <Navigate to="/login" />}
                        />
                        <Route
                            path="/insights"
                            element={token ? <Insights token={token} /> : <Navigate to="/login" />}
                        />
                        <Route path="/" element={<Navigate to={token ? "/dashboard" : "/login"} />} />
                    </Routes>
                </main>
            </div>
        </Router>
    )
}

export default App
