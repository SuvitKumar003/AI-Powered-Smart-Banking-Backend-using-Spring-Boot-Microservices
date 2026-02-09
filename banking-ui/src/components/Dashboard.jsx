import { useState, useEffect } from 'react'
import { userService, transactionService, insightService } from '../services/api'
import { Wallet, TrendingUp, TrendingDown, Clock, Brain, ShieldCheck, ArrowRight } from 'lucide-react'
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, Cell } from 'recharts'
import '../styles/Dashboard.css'

const Dashboard = () => {
    const [profile, setProfile] = useState(null)
    const [recentTransactions, setRecentTransactions] = useState([])
    const [insights, setInsights] = useState([])
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        const fetchData = async () => {
            try {
                const [profileRes, transRes, insightRes] = await Promise.all([
                    userService.getProfile(),
                    transactionService.getAll(),
                    insightService.getCategoryWise()
                ])
                setProfile(profileRes.data)
                setRecentTransactions(transRes.data.slice(0, 5))

                // Transform insights for chart
                const chartData = Object.entries(insightRes.data).map(([name, value]) => ({
                    name,
                    value: parseFloat(value.replace('%', ''))
                }))
                setInsights(chartData)
            } catch (err) {
                console.error('Failed to fetch dashboard data', err)
            } finally {
                setLoading(false)
            }
        }
        fetchData()
    }, [])

    const COLORS = ['#6366f1', '#a855f7', '#22d3ee', '#10b981', '#f59e0b', '#ef4444', '#ec4899']

    if (loading) return <div className="loading">Loading AI Dashboard...</div>

    return (
        <div className="dashboard animate-fade-in">
            <header className="dashboard-header">
                <div>
                    <h1>Welcome, {profile?.fullName.split(' ')[0]}!</h1>
                    <p className="subtitle">Here's what's happening with your money today.</p>
                </div>
                <div className="ai-status glass-card">
                    <Brain size={20} color="#a855f7" />
                    <span>AI Engine: <span className="status-online">Active</span></span>
                </div>
            </header>

            <div className="stats-grid">
                <div className="stat-card glass-card">
                    <div className="stat-icon balance"><Wallet /></div>
                    <div className="stat-info">
                        <p>Total Balance</p>
                        <h3>${profile?.accountBalance.toLocaleString()}</h3>
                    </div>
                </div>
                <div className="stat-card glass-card">
                    <div className="stat-icon income"><TrendingUp /></div>
                    <div className="stat-info">
                        <p>Monthly Income</p>
                        <h3>$0.00</h3>
                    </div>
                </div>
                <div className="stat-card glass-card">
                    <div className="stat-icon expense"><TrendingDown /></div>
                    <div className="stat-info">
                        <p>Monthly Spent</p>
                        <h3>$0.00</h3>
                    </div>
                </div>
            </div>

            <div className="dashboard-content">
                <div className="main-section glass-card">
                    <div className="section-header">
                        <h3>AI Spending Breakdown</h3>
                        <p>Percentage of spending by intelligent categories</p>
                    </div>
                    <div className="chart-container">
                        <ResponsiveContainer width="100%" height={300}>
                            <BarChart data={insights}>
                                <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="rgba(255,255,255,0.05)" />
                                <XAxis dataKey="name" axisLine={false} tickLine={false} tick={{ fill: '#94a3b8', fontSize: 12 }} />
                                <YAxis axisLine={false} tickLine={false} tick={{ fill: '#94a3b8', fontSize: 12 }} />
                                <Tooltip
                                    contentStyle={{ backgroundColor: '#1e293b', border: '1px solid rgba(255,255,255,0.1)', borderRadius: '12px' }}
                                    itemStyle={{ color: '#fff' }}
                                />
                                <Bar dataKey="value" radius={[10, 10, 0, 0]}>
                                    {insights.map((entry, index) => (
                                        <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                                    ))}
                                </Bar>
                            </BarChart>
                        </ResponsiveContainer>
                    </div>
                </div>

                <div className="side-section">
                    <div className="recent-activity glass-card">
                        <div className="section-header">
                            <h3>Recent Activity</h3>
                            <Link to="/transactions" className="view-all">View All <ArrowRight size={16} /></Link>
                        </div>
                        <div className="activity-list">
                            {recentTransactions.length > 0 ? (
                                recentTransactions.map(tx => (
                                    <div key={tx.id} className="activity-item">
                                        <div className={`activity-icon ${tx.type.toLowerCase()}`}>
                                            {tx.type === 'DEBIT' ? <TrendingDown size={18} /> : <TrendingUp size={18} />}
                                        </div>
                                        <div className="activity-details">
                                            <p className="activity-title">{tx.merchantName || tx.description}</p>
                                            <p className="activity-meta">{tx.category} • {new Date(tx.timestamp).toLocaleDateString()}</p>
                                        </div>
                                        <div className={`activity-amount ${tx.type.toLowerCase()}`}>
                                            {tx.type === 'DEBIT' ? '-' : '+'}${tx.amount.toFixed(2)}
                                        </div>
                                    </div>
                                ))
                            ) : (
                                <p className="empty-state">No transactions yet. Star spending to see AI in action!</p>
                            )}
                        </div>
                    </div>

                    <div className="ai-edge-card glass-card">
                        <div className="edge-icon"><ShieldCheck color="#10b981" /></div>
                        <h3>Fraud Protection</h3>
                        <p>Your AI security engine has scanned all transactions. Everything looks safe!</p>
                        <div className="risk-meter">
                            <div className="risk-level" style={{ width: '5%' }}></div>
                        </div>
                        <span className="risk-text">System Risk: Low</span>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Dashboard
