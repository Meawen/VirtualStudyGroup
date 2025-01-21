import { describe, it, expect, vi, beforeEach } from 'vitest';
import { render, screen, fireEvent, act } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import Login from '../Login.jsx';
import { login, register } from '../../api';

vi.mock('../../api', () => ({
    login: vi.fn(),
    register: vi.fn(),
}));

beforeEach(() => {
    vi.clearAllMocks();
    localStorage.clear();
});

describe('Login Component', () => {
    it('renders form elements correctly', () => {
        render(
            <MemoryRouter>
                <Login onLogin={vi.fn()} />
            </MemoryRouter>
        );

        expect(screen.getByPlaceholderText('Enter your email')).toBeTruthy();
        expect(screen.getByPlaceholderText('Enter your password')).toBeTruthy();
        expect(screen.getByRole('button', { name: /login/i })).toBeTruthy();
        expect(screen.getByText("Don't have an account? Register")).toBeTruthy();
    });

    it('updates state when input changes', () => {
        render(
            <MemoryRouter>
                <Login onLogin={vi.fn()} />
            </MemoryRouter>
        );

        fireEvent.change(screen.getByPlaceholderText('Enter your email'), { target: { value: 'test@example.com' } });
        fireEvent.change(screen.getByPlaceholderText('Enter your password'), { target: { value: 'password123' } });

        expect(screen.getByPlaceholderText('Enter your email').value).toBe('test@example.com');
        expect(screen.getByPlaceholderText('Enter your password').value).toBe('password123');
    });

    it('calls login API on form submission', async () => {
        login.mockResolvedValue({ token: 'mock-token' });
        const mockOnLogin = vi.fn();

        render(
            <MemoryRouter>
                <Login onLogin={mockOnLogin} />
            </MemoryRouter>
        );

        await act(async () => {
            fireEvent.change(screen.getByPlaceholderText('Enter your email'), { target: { value: 'user@test.com' } });
            fireEvent.change(screen.getByPlaceholderText('Enter your password'), { target: { value: 'password' } });
            fireEvent.click(screen.getByRole('button', { name: /login/i }));
        });

        expect(login).toHaveBeenCalledWith('user@test.com', 'password');
        expect(localStorage.getItem('token')).toBe('mock-token');
        expect(mockOnLogin).toHaveBeenCalled();
    });

    it('handles authentication error correctly', async () => {
        login.mockRejectedValue({ response: { data: { message: 'Invalid credentials' } } });

        render(
            <MemoryRouter>
                <Login onLogin={vi.fn()} />
            </MemoryRouter>
        );

        await act(async () => {
            fireEvent.change(screen.getByPlaceholderText('Enter your email'), { target: { value: 'wrong@test.com' } });
            fireEvent.change(screen.getByPlaceholderText('Enter your password'), { target: { value: 'wrongpass' } });
            fireEvent.click(screen.getByRole('button', { name: /login/i }));
        });

        expect(screen.getByText('Invalid credentials')).toBeTruthy();
    });

    it('switches between login and register modes', () => {
        render(
            <MemoryRouter>
                <Login onLogin={vi.fn()} />
            </MemoryRouter>
        );

        fireEvent.click(screen.getByText("Don't have an account? Register"));
        expect(screen.getByRole('button', { name: /register/i })).toBeTruthy();

        fireEvent.click(screen.getByText("Already have an account? Login"));
        expect(screen.getByRole('button', { name: /login/i })).toBeTruthy();
    });

    it('calls register API in register mode', async () => {
        register.mockResolvedValue({ token: 'mock-token' });
        const mockOnLogin = vi.fn();

        render(
            <MemoryRouter>
                <Login onLogin={mockOnLogin} />
            </MemoryRouter>
        );

        fireEvent.click(screen.getByText("Don't have an account? Register"));

        await act(async () => {
            fireEvent.change(screen.getByPlaceholderText('Enter your email'), { target: { value: 'newuser@test.com' } });
            fireEvent.change(screen.getByPlaceholderText('Enter your password'), { target: { value: 'newpassword' } });
            fireEvent.click(screen.getByRole('button', { name: /register/i }));
        });

        expect(register).toHaveBeenCalledWith('newuser@test.com', 'newpassword');
        expect(localStorage.getItem('token')).toBe('mock-token');
        expect(mockOnLogin).toHaveBeenCalled();
    });
});
