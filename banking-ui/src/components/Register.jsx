import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { authService } from '../services/api'
import { UserPlus, User, Mail, Lock, Phone, UserCircle, AlertCircle } from 'lucide-react'
import '../styles/Auth.css'

const Register = () => {
    const [formData, setFormData] = useState({
        username: '',
        email: '',
        password: '',
        fullName: '',
        phoneNumber: ''
    })
    const [error, setError] = useState('')
    const [loading, setLoading] = useState(false)
    const navigate = useNavigate()

    const handleSubmit = async (e) => {
        e.preventDefault()
        setLoading(true)
        setError('')
        try {
            await authService.register(formData)
            navigate('/login', { state: { message: 'Registration successful! Please login.' } })
        } catch (err) {
            setError(err.response?.data?.message || 'Registration failed. Please check your details.')
        } finally {
            setLoading(false)
        }
    }

    return (
        <div className="auth-page">
            <div className="auth-card glass-card animate-fade-in">
                <div className="auth-header">
                    <div className="auth-logo">
                        <UserPlus size={32} color="#a855f7" />
                    </div>
                    <h1>Create Account</h1>
                    <p>Join the future of AI-powered banking</p>
                </div>

                {error && (
                    <div className="error-message">
                        <AlertCircle size={18} />
                        <span>{error}</span>
                    </div>
                )}

                <form onSubmit={handleSubmit} className="auth-form">
                    <div className="input-split">
                        <div className="input-group">
                            <UserCircle className="input-icon" size={20} />
                            <input
                                type="text"
                                placeholder="Full Name"
                                className="premium-input"
                                value={formData.fullName}
                                onChange={(e) => setFormData({ ...formData, fullName: e.target.value })}
                                required
                            />
                        </div>
                        <div className="input-group">
                            <User className="input-icon" size={20} />
                            <input
                                type="text"
                                placeholder="Username"
                                className="premium-input"
                                value={formData.username}
                                onChange={(e) => setFormData({ ...formData, username: e.target.value })}
                                required
                            />
                        </div>
                    </div>

                    <div className="input-group">
                        <Mail className="input-icon" size={20} />
                        <input
                            type="email"
                            placeholder="Email Address"
                            className="premium-input"
                            value={formData.email}
                            onChange={(e) => setFormData({ ...formData, email: e.target.value })}
                            required
                        />
                    </div>

                    <div className="input-group">
                        <Phone className="input-icon" size={20} />
                        <input
                            type="text"
                            placeholder="Phone Number (10 digits)"
                            className="premium-input"
                            value={formData.phoneNumber}
                            onChange={(e) => setFormData({ ...formData, phoneNumber: e.target.value })}
                            pattern="[0-9]{10}"
                            title="Phone number must be exactly 10 digits"
                            required
                        />
                    </div>

                    <div className="input-group">
                        <Lock className="input-icon" size={20} />
                        <input
                            type="password"
                            placeholder="Password (Min 6 chars + Digit + Special)"
                            className="premium-input"
                            value={formData.password}
                            onChange={(e) => setFormData({ ...formData, password: e.target.value })}
                            required
                        />
                    </div>

                    <button type="submit" className="premium-button auth-btn" disabled={loading} style={{ background: 'linear-gradient(135deg, #a855f7, #6366f1)' }}>
                        {loading ? 'Creating Account...' : 'Get Started'}
                    </button>
                </form>

                <div className="auth-footer">
                    <p>Already have an account? <Link to="/login">Sign In</Link></p>
                </div>
            </div>
        </div>
    )
}

export default Register
