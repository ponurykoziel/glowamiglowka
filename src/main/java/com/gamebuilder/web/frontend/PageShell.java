package com.gamebuilder.web.frontend;

public final class PageShell {

    private PageShell() {
    }

    public static String render(String title, String activePath, String body) {
        return prelude(escape(title))
                + menu(activePath)
                + mid()
                + body
                + postlude();
    }

    private static String prelude(String title) {
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>""" + title + """
                </title>
                    <style>
                        :root {
                            --bg: #101418;
                            --panel: #182028;
                            --panel-2: #202a34;
                            --text: #e8edf2;
                            --muted: #9fb0c0;
                            --accent: #7cc5ff;
                            --accent-2: #9bffb0;
                            --danger: #ff8d8d;
                            --border: #2d3945;
                            --gold: #ffd700;
                        }
                        * { box-sizing: border-box; }
                        body {
                            margin: 0;
                            font-family: Arial, Helvetica, sans-serif;
                            background: var(--bg);
                            color: var(--text);
                        }
                        a { color: var(--accent); text-decoration: none; }
                        a:hover { text-decoration: underline; }
                        .layout {
                            display: grid;
                            grid-template-columns: 240px 1fr;
                            min-height: 100vh;
                        }
                        .sidebar {
                            background: #0d1217;
                            border-right: 1px solid var(--border);
                            padding: 24px 18px;
                        }
                        .brand { font-size: 24px; font-weight: bold; margin-bottom: 4px; }
                        .tagline { color: var(--muted); font-size: 13px; margin-bottom: 24px; }
                        .menu { display: flex; flex-direction: column; gap: 8px; }
                        .menu a {
                            display: block;
                            padding: 10px 12px;
                            border: 1px solid var(--border);
                            border-radius: 10px;
                            background: var(--panel);
                            color: var(--text);
                        }
                        .menu a.active {
                            border-color: var(--accent);
                            background: #163247;
                        }
                        .content { padding: 24px; }
                        .page-title { margin: 0 0 8px; font-size: 30px; }
                        .page-subtitle { margin: 0 0 24px; color: var(--muted); }
                        .grid { display: grid; gap: 16px; }
                        .card {
                            background: var(--panel);
                            border: 1px solid var(--border);
                            border-radius: 14px;
                            overflow: hidden;
                            display: flex;
                            flex-direction: column;
                        }
                        .card .card-body { padding: 14px 16px; }
                        .card h2, .card h3 { margin-top: 0; }
                        .muted { color: var(--muted); }
                        .stat { font-size: 34px; font-weight: bold; margin: 8px 0; }
                        .actions { display: flex; flex-wrap: wrap; gap: 10px; }
                        .btn {
                            display: inline-block;
                            padding: 10px 14px;
                            border-radius: 10px;
                            border: 1px solid var(--border);
                            background: var(--panel-2);
                            color: var(--text);
                            cursor: pointer;
                            font-size: 14px;
                        }
                        .btn:hover { border-color: var(--accent); }
                        .btn-small {
                            padding: 4px 8px;
                            border-radius: 6px;
                            border: 1px solid var(--border);
                            background: var(--panel-2);
                            color: var(--text);
                            cursor: pointer;
                            font-size: 12px;
                        }
                        .btn-small:hover { border-color: var(--accent); }
                        .btn-primary { background: #1d4d70; border-color: #2a6a97; }
                        .btn-good { background: #1f4f33; border-color: #2f7b4d; }
                        .btn-danger { background: #5a2a2a; border-color: #8a3d3d; }
                        .pill {
                            display: inline-block;
                            padding: 4px 8px;
                            border-radius: 999px;
                            border: 1px solid var(--border);
                            background: var(--panel-2);
                            font-size: 12px;
                        }
                        .pill.good { color: var(--accent-2); }
                        .pill.bad { color: var(--danger); }
                        .stack { display: flex; flex-direction: column; gap: 12px; }
                        .small { font-size: 12px; }
                        code {
                            background: #0c1116;
                            padding: 2px 6px;
                            border-radius: 6px;
                        }
                        input[type=text], input[type=number], textarea {
                            width: 100%;
                            padding: 10px 12px;
                            border-radius: 10px;
                            border: 1px solid var(--border);
                            background: #0f151b;
                            color: var(--text);
                        }
                        textarea { resize: vertical; min-height: 60px; }
                        .status-box { min-height: 22px; color: var(--muted); }
                        .entity-table {
                            width: 100%;
                            border-collapse: collapse;
                        }
                        .entity-table th {
                            text-align: left;
                            padding: 8px;
                            border-bottom: 1px solid var(--border);
                            color: var(--muted);
                            font-size: 12px;
                            text-transform: uppercase;
                        }
                        .entity-table td {
                            padding: 8px;
                            border-bottom: 1px solid var(--border);
                        }
                        .entity-table tr:hover td { background: rgba(255,255,255,0.02); }
                        .section { margin-bottom: 32px; }
                        .section-header {
                            display: flex;
                            justify-content: space-between;
                            align-items: center;
                            margin-bottom: 12px;
                        }
                        .section-header h2 { margin: 0; }
                        .inline-form {
                            display: flex;
                            gap: 8px;
                            align-items: flex-end;
                            flex-wrap: wrap;
                        }
                        .inline-form label { flex: 1; min-width: 120px; }
                        .inline-form label.small-label { flex: 0 0 80px; }
                        .contract-grid {
                            display: grid;
                            grid-template-columns: repeat(auto-fill, minmax(160px, 1fr));
                            gap: 8px;
                        }
                        .contract-grid label {
                            display: flex;
                            align-items: center;
                            gap: 6px;
                            font-size: 13px;
                            cursor: pointer;
                        }
                        .contract-grid input[type=checkbox] {
                            width: auto;
                            accent-color: var(--accent);
                        }
                        .error-list {
                            background: #2a1a1a;
                            border: 1px solid #5a2a2a;
                            border-radius: 10px;
                            padding: 12px 16px;
                        }
                        .error-list li { color: var(--danger); margin-bottom: 4px; }
                        .success-box {
                            background: #1a2a1a;
                            border: 1px solid #2f7b4d;
                            border-radius: 10px;
                            padding: 12px 16px;
                            color: var(--accent-2);
                        }
                        .slot-tag {
                            display: inline-block;
                            padding: 2px 8px;
                            border-radius: 6px;
                            background: var(--panel-2);
                            border: 1px solid var(--border);
                            font-size: 12px;
                            margin: 2px;
                        }
                        .slot-tag:hover { border-color: var(--accent); cursor: pointer; }
                        @media (max-width: 900px) {
                            .layout { grid-template-columns: 1fr; }
                            .sidebar {
                                border-right: 0;
                                border-bottom: 1px solid var(--border);
                            }
                        }
                    </style>
                </head>
                <body>
                    <div class="layout">
                        <aside class="sidebar">
                            <div class="brand">gamebuilder</div>
                            <div class="tagline">formal game definition studio</div>
                            <nav class="menu">
                """;
    }

    private static String mid() {
        return """
                            </nav>
                        </aside>
                        <main class="content">
                """;
    }

    private static String postlude() {
        return """
                        </main>
                    </div>
                </body>
                </html>
                """;
    }

    private static String menu(String activePath) {
        return link("/", "Studio", activePath)
                + link("/q/swagger-ui", "OpenAPI", activePath);
    }

    private static String link(String href, String label, String activePath) {
        String active = href.equals(activePath) ? "active" : "";
        return "<a class=\"" + active + "\" href=\"" + href + "\">" + escape(label) + "</a>";
    }

    public static String escape(String value) {
        if (value == null) {
            return "";
        }
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
}
