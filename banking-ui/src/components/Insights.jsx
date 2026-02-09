import { useState, useEffect } from 'react'
import { insightService } from '../services/api'
import { PieChart as PieChartIcon, Target, Zap, Lightbulb } from 'lucide-react'
import { PieChart, Pie, Cell, Tooltip, ResponsiveContainer, Legend } from 'recharts'
import '../styles/Insights.css'

const Insights = () => {
    const [categoryData, setCategoryData] = useState([])
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        const fetchInsights = async () => {
            try {
                const res = await insightService.getCategoryWise()
                const formattedData = Object.entries(res.data).map(([name, value]) => ({
                    name,
                    value: parseFloat(value.replace('%', ''))
                }))
                setCategoryData(formattedData)
            } catch (err) {
                console.error('Error fetching insights', err)
            } finally {
                setLoading(false)
            }
        }
        fetchInsights()
    }, [])

    const COLORS = ['#6366f1', '#a855f7', '#22d3ee', '#10b981', '#f59e0b', '#ef4444', '#ec4899']

    if (loading) return <div className="loading">Analyzing Financial Patterns...</div>

    return (
        <div className="insights-page animate-fade-in">
            <header className="page-header">
                <h1>AI Financial Insights</h1>
                <p className="subtitle">Smart analysis of your spending habits.</p>
            </header>

            <div className="insights-grid">
                <div className="insight-chart-card glass-card">
                    <h3>Spending Distribution</h3>
                    <div className="pie-container">
                        <ResponsiveContainer width="100%" height={400}>
                            <PieChart>
                                <Pie
                                    data={categoryData}
                                    cx="50%"
                                    cy="50%"
                                    innerRadius={80}
                                    outerRadius={120}
                                    paddingAngle={5}
                                    dataKey="value"
                                >
                                    {categoryData.map((entry, index) => (
                                        <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                                    ))}
                                </Pie>
                                <Tooltip
                                    contentStyle={{ backgroundColor: '#1e293b', border: '1px solid rgba(255,255,255,0.1)', borderRadius: '12px' }}
                                    itemStyle={{ color: '#fff' }}
                                />
                                <Legend iconType="circle" />
                            </PieChart>
                        </ResponsiveContainer>
                    </div>
                </div>

                <div className="ai-tips-section">
                    <div className="tip-card glass-card">
                        <div className="tip-icon"><Lightbulb color="#fbbf24" /></div>
                        <div className="tip-content">
                            <h4>Smart Tip</h4>
                            <p>You spent 15% more on <strong>Food</strong> this week compared to last. Consider checking for subscription renewals or dining out trends.</p>
                        </div>
                    </div>

                    <div className="tip-card glass-card">
                        <div className="tip-icon"><Target color="#ec4899" /></div>
                        <div className="tip-content">
                            <h4>Saving Goal</h4>
                            <p>You're only $250 away from your <strong>Emergency Fund</strong> goal of $5,000. Keep it up!</p>
                        </div>
                    </div>

                    <div className="tip-card glass-card premium-tip">
                        <div className="tip-icon"><Zap color="#22d3ee" /></div>
                        <div className="tip-content">
                            <h4>AI Prediction</h4>
                            <p>Based on your current trajectory, you will have <strong>$1,200</strong> surplus by the end of the month!</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Insights
