import { describe, it, expect, vi, beforeEach } from 'vitest';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import App from './App';
import * as auth from './utils/auth';

// Mocking getUsernameFromToken
vi.mock('./utils/auth', () => ({
    getUsernameFromToken: vi.fn(),
}));

beforeEach(() => {
    vi.clearAllMocks();
    localStorage.clear();
});

describe('App Component', () => {
    it('renders header and navigation links', () => {
        auth.getUsernameFromToken.mockReturnValue(null);

        render(
            <MemoryRouter>
                <App />
            </MemoryRouter>
        );

        expect(screen.getByText('Virtual Study Group Platform')).toBeTruthy();
        expect(screen.getByText('Home')).toBeTruthy();
        expect(screen.getByText('Create Group')).toBeTruthy();
        expect(screen.getByText('Learning Goals')).toBeTruthy();
        expect(screen.getByText('Add Note')).toBeTruthy();
    });

    it('shows login link when user is not logged in', () => {
        auth.getUsernameFromToken.mockReturnValue(null);

        render(
            <MemoryRouter>
                <App />
            </MemoryRouter>
        );

        expect(screen.getByText('Login')).toBeTruthy();
    });

    it('shows username and logout button when user is logged in', () => {
        auth.getUsernameFromToken.mockReturnValue('testuser');

        render(
            <MemoryRouter>
                <App />
            </MemoryRouter>
        );

        expect(screen.getByText('Welcome, testuser')).toBeTruthy();
        expect(screen.getByText('Logout')).toBeTruthy();
    });

    it('logs out user and redirects to login', async () => {
        auth.getUsernameFromToken.mockReturnValue('testuser');

        render(
            <MemoryRouter>
                <App />
            </MemoryRouter>
        );

        fireEvent.click(screen.getByText('Logout'));

        await waitFor(() => {
            expect(localStorage.getItem('token')).toBeNull();
            expect(screen.getByText('Login')).toBeTruthy();
        });
    });

    it('navigates to create group page', async () => {
        render(
            <MemoryRouter initialEntries={['/']}>
                <App />
            </MemoryRouter>
        );

        fireEvent.click(screen.getByText('Create Group'));

        await waitFor(() => {
            expect(screen.getByText('Create a Study Group')).toBeTruthy();
        });
    });

    it('navigates to learning goals page', async () => {
        render(
            <MemoryRouter initialEntries={['/']}>
                <App />
            </MemoryRouter>
        );

        fireEvent.click(screen.getByText('Learning Goals'));

        await waitFor(() => {
            expect(screen.getByText('Define Learning Goal')).toBeTruthy();
        });
    });

    it('navigates to add note page', async () => {
        render(
            <MemoryRouter initialEntries={['/']}>
                <App />
            </MemoryRouter>
        );

        fireEvent.click(screen.getByText('Add Note'));

        await waitFor(() => {
            expect(screen.getByText('Add a New Note')).toBeTruthy();
        });
    });

    it('navigates to login page when clicking login link', () => {
        auth.getUsernameFromToken.mockReturnValue(null);

        render(
            <MemoryRouter>
                <App />
            </MemoryRouter>
        );

        fireEvent.click(screen.getByText('Login'));

        expect(screen.getAllByText('Login')).toBeTruthy();
    });

    it('displays footer correctly', () => {
        render(
            <MemoryRouter>
                <App />
            </MemoryRouter>
        );

        expect(screen.getByText(`© ${new Date().getFullYear()} Virtual Study Group Platform`)).toBeTruthy();
    });
});
