import { describe, it, expect, vi, beforeEach } from 'vitest';
import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import ProtectedRoute from '../ProtectedRoute.jsx';

const TestComponent = () => <div>Protected Content</div>;

describe('ProtectedRoute Component', () => {
    beforeEach(() => {
        localStorage.clear();
    });

    it('redirects to login if no token is found', () => {
        const { container } = render(
            <MemoryRouter initialEntries={['/protected']}>
                <ProtectedRoute>
                    <TestComponent />
                </ProtectedRoute>
            </MemoryRouter>
        );

        expect(container.innerHTML).toMatch('login');
    });

    it('renders children when token is present', () => {
        localStorage.setItem('token', 'valid-token');

        const { getByText } = render(
            <MemoryRouter initialEntries={['/protected']}>
                <ProtectedRoute>
                    <TestComponent />
                </ProtectedRoute>
            </MemoryRouter>
        );

        expect(getByText('Protected Content')).toBeTruthy();
    });
});
