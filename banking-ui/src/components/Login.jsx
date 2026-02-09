import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { authService } from '../services/api'
import { LogIn, User, Lock, AlertCircle } from 'lucide-react'
import '../styles/Auth.css'

const Login = ({ setToken, setUser }) => {
    const [formData, setFormData] = useState({ usernameOrEmail: '', password: '' })
    const [error, setError] = useState('')
    const [loading, setLoading] = useState(false)
    const navigate = useNavigate()

    const handleSubmit = async (e) => {
        e.preventDefault()
        setLoading(true)
        setError('')
        try {
            const response = await authService.login(formData)
            setToken(response.data.token)
            setUser({
                username: response.data.username,
                email: response.data.email,
                fullName: response.data.fullName
            })
            navigate('/dashboard')
        } catch (err) {
            setError(err.response?.data?.message || 'Invalid credentials. Please try again.')
        } finally {
            setLoading(false)
        }
    }

    return (
        <div className="auth-page">
            <div className="auth-card glass-card animate-fade-in">
                <div className="auth-header">
                    <div className="auth-logo">
                        <LogIn size={32} color="#6366f1" />
                    </div>
                    <h1>Welcome Back</h1>
                    <p>Login to access your AI-powered smart bank</p>
                </div>

                {error && (
                    <div className="error-message">
                        <AlertCircle size={18} />
                        <span>{error}</span>
                    </div>
                )}

                <form onSubmit={handleSubmit} className="auth-form">
                    <div className="input-group">
                        <User className="input-icon" size={20} />
                        <input
                            type="text"
                            placeholder="Username or Email"
                            className="premium-input"
                            value={formData.usernameOrEmail}
                            onChange={(e) => setFormData({ ...formData, usernameOrEmail: e.target.value })}
                            required
                        />
                    </div>

                    <div className="input-group">
                        <Lock className="input-icon" size={20} />
                        <input
                            type="password"
                            placeholder="Password"
                            className="premium-input"
                            value={formData.password}
                            onChange={(e) => setFormData({ ...formData, password: e.target.value })}
                            required
                        />
                    </div>

                    <button type="submit" className="premium-button auth-btn" disabled={loading}>
                        {loading ? 'Authenticating...' : 'Sign In'}
                    </button>
                </form>

                <div className="auth-footer">
                    <p>Don't have an account? <Link to="/register">Create one</Link></p>
                </div>
            </div>
        </div>
    )
}

export default Login
