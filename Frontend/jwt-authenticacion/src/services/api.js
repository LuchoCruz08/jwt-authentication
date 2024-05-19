const API_URL = 'http://localhost:8081/auth'

export const registerUser = async (userData) => {
    const response = await fetch(`${API_URL}/signup`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(userData),
    })

    if(!response.ok) {
        throw new Error('Failed to register user')
    }

    return response.json()
}

export const loginUser = async (userData) => {
    const response = await fetch(`${API_URL}/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(userData),
    })

    if(!response.ok) {
        throw new Error('Failed to login user')
    }

    return response.json()
}