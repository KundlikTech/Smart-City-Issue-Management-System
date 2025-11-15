import React from 'react';
import styles from './AuthContent.module.css';

/**
 * Re-usable auth layout wrapper used by Login / Register / ForgotPassword pages.
 * Usage:
 * <AuthContent title="Sign in">
 *   { /* form or children here *\/ }
 * </AuthContent>
 */

export default function AuthContent({ title, children, subtitle }) {
  return (
    <div className={styles.wrapper}>
      <div className={styles.card}>
        <header className={styles.header}>
          <h1 className={styles.title}>{title}</h1>
          {subtitle && <p className={styles.subtitle}>{subtitle}</p>}
        </header>

        <main className={styles.body}>{children}</main>

        <footer className={styles.footer}>
          <small>SmartCity · Demo</small>
        </footer>
      </div>
    </div>
  );
}
