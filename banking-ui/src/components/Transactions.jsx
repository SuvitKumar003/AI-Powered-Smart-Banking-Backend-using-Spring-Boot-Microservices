import { useState, useEffect } from 'react'
import { transactionService } from '../services/api'
import { Plus, Search, Filter, ArrowUpRight, ArrowDownLeft, Brain, ShieldAlert } from 'lucide-react'
import '../styles/Transactions.css'

const Transactions = () => {
    const [transactions, setTransactions] = useState([])
    const [loading, setLoading] = useState(true)
    const [showAddModal, setShowAddModal] = useState(false)
    const [newTx, setNewTx] = useState({
        type: 'DEBIT',
        amount: '',
        description: '',
        merchantName: '',
        location: ''
    })
    const [processing, setProcessing] = useState(false)

    useEffect(() => {
        fetchTransactions()
    }, [])

    const fetchTransactions = async () => {
        try {
            const res = await transactionService.getAll()
            setTransactions(res.data)
        } catch (err) {
            console.error('Error fetching transactions', err)
        } finally {
            setLoading(false)
        }
    }

    const handleAddTx = async (e) => {
        e.preventDefault()
        setProcessing(true)
        try {
            await transactionService.create({
                ...newTx,
                amount: parseFloat(newTx.amount)
            })
            setShowAddModal(false)
            setNewTx({ type: 'DEBIT', amount: '', description: '', merchantName: '', location: '' })
            fetchTransactions()
        } catch (err) {
            const errorMessage = err.response?.data?.message || 'Transaction failed. Please try again.';
            alert(errorMessage);
        } finally {
            setProcessing(false)
        }
    }

    return (
        <div className="transactions-page animate-fade-in">
            <header className="page-header">
                <h1>All Transactions</h1>
                <button className="premium-button add-tx-btn" onClick={() => setShowAddModal(true)}>
                    <Plus size={20} />
                    <span>New Transaction</span>
                </button>
            </header>

            <div className="filters-bar glass-card">
                <div className="search-box">
                    <Search size={18} />
                    <input type="text" placeholder="Search transactions..." />
                </div>
                <div className="filter-actions">
                    <button className="filter-btn"><Filter size={18} /> Filter</button>
                </div>
            </div>

            <div className="transactions-list glass-card">
                <table className="tx-table">
                    <thead>
                        <tr>
                            <th>Date</th>
                            <th>Description</th>
                            <th>Category</th>
                            <th>Fraud Risk</th>
                            <th>Amount</th>
                        </tr>
                    </thead>
                    <tbody>
                        {transactions.length > 0 ? (
                            transactions.map(tx => (
                                <tr key={tx.id}>
                                    <td>{new Date(tx.timestamp).toLocaleDateString()}</td>
                                    <td>
                                        <div className="tx-desc-cell">
                                            <span className="tx-merchant">{tx.merchantName || 'Unknown'}</span>
                                            <span className="tx-desc">{tx.description}</span>
                                        </div>
                                    </td>
                                    <td>
                                        <span className={`tx-category ${tx.category.toLowerCase()}`}>
                                            <Brain size={12} style={{ marginRight: '4px' }} />
                                            {tx.category}
                                        </span>
                                    </td>
                                    <td>
                                        <div className="tx-risk-cell">
                                            <div className="risk-indicator">
                                                <div
                                                    className={`risk-dot ${tx.fraudRiskScore > 0.5 ? 'high' : 'low'}`}
                                                />
                                                <span>{tx.fraudRiskScore.toFixed(2)}</span>
                                            </div>
                                        </div>
                                    </td>
                                    <td className={`tx-amount ${tx.type.toLowerCase()}`}>
                                        {tx.type === 'DEBIT' ? '-' : '+'}${tx.amount.toFixed(2)}
                                    </td>
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan="5" className="empty-table">No transactions found.</td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>

            {showAddModal && (
                <div className="modal-overlay">
                    <div className="modal-content glass-card animate-fade-in">
                        <h2>New Smart Transaction</h2>
                        <p className="modal-subtitle">AI will categorize this automatically.</p>

                        <form onSubmit={handleAddTx} className="modal-form">
                            <div className="form-group">
                                <label>Transaction Type</label>
                                <select
                                    className="premium-input"
                                    value={newTx.type}
                                    onChange={(e) => setNewTx({ ...newTx, type: e.target.value })}
                                >
                                    <option value="DEBIT">Debit (Spend)</option>
                                    <option value="CREDIT">Credit (Income)</option>
                                </select>
                            </div>

                            <div className="form-group">
                                <label>Amount ($)</label>
                                <input
                                    type="number"
                                    step="0.01"
                                    className="premium-input"
                                    placeholder="0.00"
                                    value={newTx.amount}
                                    onChange={(e) => setNewTx({ ...newTx, amount: e.target.value })}
                                    required
                                />
                            </div>

                            <div className="form-group">
                                <label>Description (Describe what you bought)</label>
                                <input
                                    type="text"
                                    className="premium-input"
                                    placeholder="e.g. Flight to London, Pizza night"
                                    value={newTx.description}
                                    onChange={(e) => setNewTx({ ...newTx, description: e.target.value })}
                                    required
                                />
                            </div>

                            <div className="input-split">
                                <div className="form-group">
                                    <label>Merchant</label>
                                    <input
                                        type="text"
                                        className="premium-input"
                                        placeholder="Merchant Name"
                                        value={newTx.merchantName}
                                        onChange={(e) => setNewTx({ ...newTx, merchantName: e.target.value })}
                                    />
                                </div>
                                <div className="form-group">
                                    <label>Location</label>
                                    <input
                                        type="text"
                                        className="premium-input"
                                        placeholder="Location"
                                        value={newTx.location}
                                        onChange={(e) => setNewTx({ ...newTx, location: e.target.value })}
                                    />
                                </div>
                            </div>

                            <div className="modal-actions">
                                <button type="button" className="secondary-btn" onClick={() => setShowAddModal(false)}>Cancel</button>
                                <button type="submit" className="premium-button" disabled={processing}>
                                    {processing ? 'Processing AI...' : 'Add Transaction'}
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </div>
    )
}

export default Transactions
