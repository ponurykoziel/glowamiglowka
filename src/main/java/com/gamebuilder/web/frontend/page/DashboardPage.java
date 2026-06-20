package com.gamebuilder.web.frontend.page;

import com.gamebuilder.web.frontend.PageShell;

public final class DashboardPage {

    private DashboardPage() {
    }

    public static String render() {
        String body = """
                <h1 class="page-title">Studio</h1>
                <p class="page-subtitle">Define your game ontology and compose definitions</p>

                <div class="studio-layout">
                    <!-- LEFT: Editors -->
                    <div class="studio-left">

                        <!-- Realms -->
                        <div class="card" style="margin-bottom:12px;">
                            <div class="card-body">
                                <div class="section-header" style="margin-bottom:8px;">
                                    <h3 style="margin:0;">Realms <span class="muted small" id="realm-count-label">0</span></h3>
                                </div>
                                <form id="realm-form" class="inline-form" style="margin-bottom:8px;">
                                    <input type="text" id="realm-name" placeholder="realm name" required style="flex:1;">
                                    <button type="submit" class="btn btn-small btn-primary" style="margin-bottom:0;">+</button>
                                </form>
                                <div id="realm-status" class="status-box"></div>
                                <div id="realm-list" style="margin-top:4px;font-size:13px;"></div>
                            </div>
                        </div>

                        <!-- Components -->
                        <div class="card" style="margin-bottom:12px;">
                            <div class="card-body">
                                <div class="section-header" style="margin-bottom:8px;">
                                    <h3 style="margin:0;">Components <span class="muted small" id="component-count-label">0</span></h3>
                                </div>
                                <form id="component-form" class="inline-form" style="margin-bottom:8px;">
                                    <input type="text" id="component-name" placeholder="component name" required style="flex:1;">
                                    <select id="component-realm" style="flex:1;padding:8px;border-radius:8px;border:1px solid var(--border);background:#0f151b;color:var(--text);"></select>
                                    <button type="submit" class="btn btn-small btn-primary" style="margin-bottom:0;">+</button>
                                </form>
                                <div id="component-status" class="status-box"></div>
                                <div id="component-list" style="margin-top:4px;font-size:13px;"></div>
                            </div>
                        </div>

                        <!-- Operators -->
                        <div class="card" style="margin-bottom:12px;">
                            <div class="card-body">
                                <div class="section-header" style="margin-bottom:8px;">
                                    <h3 style="margin:0;">Operators <span class="muted small" id="operator-count-label">0</span></h3>
                                </div>
                                <form id="operator-form" class="stack" style="gap:6px;">
                                    <div class="inline-form">
                                        <input type="text" id="operator-name" placeholder="operator name" required style="flex:1;">
                                        <select id="operator-arity" style="flex:0 0 90px;padding:8px;border-radius:8px;border:1px solid var(--border);background:#0f151b;color:var(--text);">
                                            <option value="1">Unary</option>
                                            <option value="2" selected>Binary</option>
                                            <option value="3">Ternary</option>
                                        </select>
                                    </div>
                                    <div id="operator-slots"></div>
                                    <details style="font-size:12px;">
                                        <summary style="cursor:pointer;color:var(--muted);">Contract properties</summary>
                                        <div class="contract-grid" id="contract-grid" style="margin-top:6px;"></div>
                                    </details>
                                    <button type="submit" class="btn btn-small btn-primary">Add Operator</button>
                                </form>
                                <div id="operator-status" class="status-box"></div>
                                <div id="operator-list" style="margin-top:4px;font-size:13px;"></div>
                            </div>
                        </div>

                        <!-- Artifacts -->
                        <div class="card" style="margin-bottom:12px;">
                            <div class="card-body">
                                <div class="section-header" style="margin-bottom:8px;">
                                    <h3 style="margin:0;">Artifacts <span class="muted small" id="artifact-count-label">0</span></h3>
                                </div>
                                <form id="artifact-form" class="inline-form" style="margin-bottom:8px;">
                                    <select id="artifact-entity-id" style="flex:1;padding:8px;border-radius:8px;border:1px solid var(--border);background:#0f151b;color:var(--text);"></select>
                                    <input type="text" id="artifact-description" placeholder="description" required style="flex:2;">
                                    <button type="submit" class="btn btn-small btn-primary" style="margin-bottom:0;">+</button>
                                </form>
                                <div id="artifact-status" class="status-box"></div>
                                <div id="artifact-list" style="margin-top:4px;font-size:13px;"></div>
                            </div>
                        </div>
                    </div>

                    <!-- RIGHT: Composer + Ask AI -->
                    <div class="studio-right">

                        <!-- Composer + Control -->
                        <div class="card" style="margin-bottom:16px;">
                            <div class="card-body">
                                <h2>Game Definition Composer</h2>
                                <p class="muted small">Select entities from your palette, compose an ephemeral definition, and validate it.</p>

                                <div class="compose-selectors">
                                    <div class="compose-col">
                                        <h4>Realms</h4>
                                        <div id="compose-realms" class="compose-checklist"></div>
                                    </div>
                                    <div class="compose-col">
                                        <h4>Components</h4>
                                        <div id="compose-components" class="compose-checklist"></div>
                                    </div>
                                    <div class="compose-col">
                                        <h4>Operators</h4>
                                        <div id="compose-operators" class="compose-checklist"></div>
                                    </div>
                                </div>

                                <div class="actions" style="margin-top:12px;">
                                    <button id="compose-btn" class="btn btn-good">Compose &amp; Validate</button>
                                    <button id="compose-select-all-btn" class="btn btn-small">Select All</button>
                                    <button id="compose-deselect-all-btn" class="btn btn-small">Deselect All</button>
                                    <button id="reset-palette-btn" class="btn btn-small btn-danger">Reset (Bomberman)</button>
                                    <button id="clear-palette-btn" class="btn btn-small btn-danger">Clear Palette</button>
                                </div>
                                <div id="compose-status" class="status-box" style="margin-top:8px;"></div>
                            </div>
                        </div>

                        <div id="compose-result"></div>

                        <!-- Ask AI -->
                        <div class="card" style="margin-top:16px;">
                            <div class="card-body">
                                <h2>Ask AI</h2>
                                <p class="muted small">Describe what you want to build and let the AI suggest entities.</p>
                                <form id="ask-ai-form" class="stack" style="gap:8px;">
                                    <textarea id="ask-ai-prompt" placeholder="e.g. Add a health system with damage and healing operators..." rows="4"></textarea>
                                    <button type="submit" class="btn btn-primary">Send</button>
                                </form>
                                <div id="ask-ai-status" class="status-box" style="margin-top:8px;"></div>
                                <div id="ask-ai-response" style="margin-top:8px;font-size:13px;"></div>
                            </div>
                        </div>
                    </div>
                </div>

                <style>
                .studio-layout {
                    display: grid;
                    grid-template-columns: 1fr 1fr;
                    gap: 24px;
                    align-items: start;
                }
                .studio-left, .studio-right {
                    min-width: 0;
                }
                .compose-selectors {
                    display: grid;
                    grid-template-columns: 1fr 1fr 1fr;
                    gap: 12px;
                }
                .compose-col h4 {
                    margin: 0 0 6px;
                    font-size: 13px;
                    color: var(--muted);
                    text-transform: uppercase;
                }
                .compose-checklist {
                    max-height: 200px;
                    overflow-y: auto;
                    border: 1px solid var(--border);
                    border-radius: 8px;
                    padding: 6px;
                    background: #0f151b;
                    font-size: 12px;
                }
                .compose-checklist label {
                    display: flex;
                    align-items: center;
                    gap: 6px;
                    padding: 3px 4px;
                    cursor: pointer;
                    border-radius: 4px;
                }
                .compose-checklist label:hover { background: rgba(255,255,255,0.03); }
                .compose-checklist input[type=checkbox] {
                    width: auto;
                    accent-color: var(--accent);
                }
                .entity-row {
                    display: flex;
                    align-items: center;
                    gap: 6px;
                    padding: 2px 0;
                }
                .entity-row .entity-name { flex: 1; }
                .entity-row .entity-id { color: var(--muted); font-size: 11px; }
                .entity-row .btn-micro {
                    padding: 1px 5px;
                    border-radius: 4px;
                    border: 1px solid var(--border);
                    background: var(--panel-2);
                    color: var(--danger);
                    cursor: pointer;
                    font-size: 11px;
                    line-height: 1;
                }
                .entity-row .btn-micro:hover { border-color: var(--danger); }
                .slot-tag {
                    display: inline-block;
                    padding: 2px 6px;
                    border-radius: 4px;
                    background: var(--panel-2);
                    border: 1px solid var(--border);
                    font-size: 11px;
                    margin: 1px;
                    cursor: pointer;
                }
                .slot-tag:hover { border-color: var(--accent); }
                .slot-tag.selected {
                    border-color: var(--accent);
                    background: #163247;
                }
                .slot-select {
                    display: flex;
                    flex-wrap: wrap;
                    gap: 3px;
                    padding: 6px;
                    border: 1px solid var(--border);
                    border-radius: 8px;
                    background: #0f151b;
                    min-height: 32px;
                    align-items: center;
                }
                .slot-select .muted { font-size: 11px; }
                .slot-tag-row {
                    display: flex;
                    flex-wrap: wrap;
                    gap: 3px;
                    margin-top: 2px;
                    margin-bottom: 6px;
                }
                .op-sep { color: var(--danger); }
                .compose-result-card {
                    background: var(--panel);
                    border: 1px solid var(--border);
                    border-radius: 14px;
                    overflow: hidden;
                }
                .compose-result-card .card-body { padding: 14px 16px; }
                @media (max-width: 1000px) {
                    .studio-layout { grid-template-columns: 1fr; }
                    .compose-selectors { grid-template-columns: 1fr; }
                }
                </style>

                <script>
                var paletteData = null;
                var slotSelections = [];

                function h(s) {
                    return s ? s.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/"/g,'&quot;') : '';
                }

                function randomDefId() {
                    return 'def-' + Math.random().toString(16).substring(2, 10);
                }

                async function loadPalette() {
                    try {
                        var res = await fetch('/api/gamebuilder/palette');
                        paletteData = await res.json();
                        renderRealmSelect();
                        renderEntityDropdown();
                        renderRealmList();
                        renderComponentList();
                        renderOperatorList();
                        renderComposeChecklists();
                        renderOperatorSlots();
                        refreshSlotDisplays();
                        document.getElementById('realm-count-label').textContent = paletteData.realms.length;
                        document.getElementById('component-count-label').textContent = paletteData.components.length;
                        document.getElementById('operator-count-label').textContent = paletteData.operators.length;
                        loadArtifacts();
                    } catch(e) {
                        console.error('Failed to load palette', e);
                    }
                }

                // ── Realms ──────────────────────────────────────────────

                function renderRealmSelect() {
                    var sel = document.getElementById('component-realm');
                    sel.innerHTML = paletteData.realms.map(function(r) {
                        return `<option value="${r.id}">${h(r.name)} (${r.id})</option>`;
                    }).join('');
                }

                function renderRealmList() {
                    var realms = paletteData.realms;
                    if (realms.length === 0) {
                        document.getElementById('realm-list').innerHTML = '<span class="muted small">none</span>';
                        return;
                    }
                    document.getElementById('realm-list').innerHTML = realms.map(function(r) {
                        return `<div class="entity-row"><span class="entity-name">${h(r.name)}</span><code class="entity-id">${r.id}</code><button class="btn-micro" onclick="removeRealm('${r.id}')">\\u2715</button></div>`;
                    }).join('');
                }

                async function removeRealm(id) {
                    if (!confirm('Delete realm ' + id + '?')) return;
                    await fetch('/api/gamebuilder/realms/' + id, { method: 'DELETE' });
                    loadPalette();
                }

                document.getElementById('realm-form').addEventListener('submit', async function(e) {
                    e.preventDefault();
                    var name = document.getElementById('realm-name').value.trim();
                    if (!name) return;
                    var el = document.getElementById('realm-status');
                    el.textContent = '...';
                    var res = await fetch('/api/gamebuilder/realms', {
                        method: 'POST', headers: {'Content-Type': 'application/json'},
                        body: JSON.stringify({ name: name })
                    });
                    if (res.ok) { el.innerHTML = '<span class="pill good">ok</span>'; document.getElementById('realm-name').value = ''; loadPalette(); }
                    else { var err = await res.json(); el.innerHTML = '<span class="pill bad">' + h(err.error) + '</span>'; }
                });

                // ── Components ──────────────────────────────────────────

                function renderComponentList() {
                    var comps = paletteData.components;
                    if (comps.length === 0) {
                        document.getElementById('component-list').innerHTML = '<span class="muted small">none</span>';
                        return;
                    }
                    document.getElementById('component-list').innerHTML = comps.map(function(c) {
                        var r = paletteData.realms.find(function(rr) { return rr.id === c.realmId; });
                        var realmLabel = r ? r.name : c.realmId;
                        return `<div class="entity-row"><span class="entity-name">${h(c.name)}</span><code class="entity-id">${c.id}</code><span class="muted small">in ${h(realmLabel)}</span><button class="btn-micro" onclick="removeComponent('${c.id}')">\\u2715</button></div>`;
                    }).join('');
                }

                async function removeComponent(id) {
                    if (!confirm('Delete component ' + id + '?')) return;
                    await fetch('/api/gamebuilder/components/' + id, { method: 'DELETE' });
                    loadPalette();
                }

                document.getElementById('component-form').addEventListener('submit', async function(e) {
                    e.preventDefault();
                    var name = document.getElementById('component-name').value.trim();
                    var realmId = document.getElementById('component-realm').value;
                    if (!name || !realmId) return;
                    var el = document.getElementById('component-status');
                    el.textContent = '...';
                    var res = await fetch('/api/gamebuilder/components', {
                        method: 'POST', headers: {'Content-Type': 'application/json'},
                        body: JSON.stringify({ name: name, realmId: realmId })
                    });
                    if (res.ok) { el.innerHTML = '<span class="pill good">ok</span>'; document.getElementById('component-name').value = ''; loadPalette(); }
                    else { var err = await res.json(); el.innerHTML = '<span class="pill bad">' + h(err.error) + '</span>'; }
                });

                // ── Operators ───────────────────────────────────────────

                function renderOperatorSlots() {
                    var arity = parseInt(document.getElementById('operator-arity').value);
                    var slotNames = arity === 1 ? ['Operands'] : arity === 2 ? ['LHS', 'RHS'] : ['Left', 'Middle', 'Right'];
                    var comps = paletteData ? paletteData.components : [];
                    var html = '';
                    for (var i = 0; i < arity; i++) {
                        html += `<div style="font-size:12px;color:var(--muted);">${slotNames[i]}</div>`;
                        html += `<div class="slot-select" data-slot="${i}"><span class="muted">click tags below</span></div>`;
                        html += `<div class="slot-tag-row" data-slot-tags="${i}">`;
                        comps.forEach(function(c) {
                            html += `<span class="slot-tag" data-slot="${i}" data-comp-id="${c.id}" onclick="toggleSlotTag(${i},'${c.id}')">${h(c.name)}</span>`;
                        });
                        html += '</div>';
                    }
                    document.getElementById('operator-slots').innerHTML = html;
                }

                function toggleSlotTag(slotIndex, compId) {
                    var arity = parseInt(document.getElementById('operator-arity').value);
                    if (slotSelections.length !== arity) {
                        slotSelections = [];
                        for (var i = 0; i < arity; i++) slotSelections.push([]);
                    }
                    var idx = slotSelections[slotIndex].indexOf(compId);
                    if (idx >= 0) {
                        slotSelections[slotIndex].splice(idx, 1);
                    } else {
                        slotSelections[slotIndex].push(compId);
                    }
                    refreshSlotDisplays();
                }

                function refreshSlotDisplays() {
                    var arity = parseInt(document.getElementById('operator-arity').value);
                    if (slotSelections.length !== arity) {
                        slotSelections = [];
                        for (var i = 0; i < arity; i++) slotSelections.push([]);
                    }
                    for (var i = 0; i < arity; i++) {
                        var el = document.querySelector(`.slot-select[data-slot="${i}"]`);
                        if (!el) continue;
                        if (slotSelections[i].length === 0) {
                            el.innerHTML = '<span class="muted">click tags below</span>';
                        } else {
                            el.innerHTML = slotSelections[i].map(function(cid) {
                                var c = paletteData.components.find(function(cc) { return cc.id === cid; });
                                return `<span class="slot-tag selected" onclick="toggleSlotTag(${i},'${cid}')">${h(c ? c.name : cid)} \\u2715</span>`;
                            }).join('');
                        }
                    }
                    // Highlight tags per-slot
                    for (var i = 0; i < arity; i++) {
                        document.querySelectorAll(`.slot-tag[data-slot="${i}"][data-comp-id]`).forEach(function(tag) {
                            var cid = tag.getAttribute('data-comp-id');
                            var inSlot = slotSelections[i].indexOf(cid) >= 0;
                            tag.className = 'slot-tag' + (inSlot ? ' selected' : '');
                        });
                    }
                }

                function buildContractGrid() {
                    var props = ['reflexive','irreflexive','antisymmetric','asymmetric','idempotent','involutive','monotonic','associative','cancellative','distributive','transitive','identityElement','inverseElement','absorbingElement'];
                    document.getElementById('contract-grid').innerHTML = props.map(function(p) {
                        return `<label><input type="checkbox" id="contract-${p}"> ${p}</label>`;
                    }).join('');
                }

                function getContractFromForm() {
                    var props = ['reflexive','irreflexive','antisymmetric','asymmetric','idempotent','involutive','monotonic','associative','cancellative','distributive','transitive','identityElement','inverseElement','absorbingElement'];
                    var c = {};
                    props.forEach(function(p) { c[p] = document.getElementById('contract-' + p).checked; });
                    return c;
                }

                function renderOperatorList() {
                    var ops = paletteData.operators;
                    if (ops.length === 0) {
                        document.getElementById('operator-list').innerHTML = '<span class="muted small">none</span>';
                        return;
                    }
                    document.getElementById('operator-list').innerHTML = ops.map(function(o) {
                        var slotLabels = o.operands.map(function(slot) {
                            return slot.map(function(cid) {
                                var c = paletteData.components.find(function(cc) { return cc.id === cid; });
                                return c ? c.name : cid;
                            }).join(', ');
                        }).join('<span class="op-sep"> || </span>');
                        var contractParts = [];
                        ['reflexive','irreflexive','antisymmetric','asymmetric','idempotent','involutive','monotonic','associative','cancellative','distributive','transitive','identityElement','inverseElement','absorbingElement'].forEach(function(p) {
                            if (o.contract[p]) contractParts.push(p);
                        });
                        var contractLine = contractParts.length ? '<br><span class="muted small">' + contractParts.join(', ') + '</span>' : '';
                        return `<div class="entity-row"><span class="entity-name">${h(o.name)}</span><code class="entity-id">${o.id}</code><span class="muted small">${o.operandCount}-arg (${slotLabels})${contractLine}</span><button class="btn-micro" onclick="removeOperator('${o.id}')">\\u2715</button></div>`;
                    }).join('');
                }

                async function removeOperator(id) {
                    if (!confirm('Delete operator ' + id + '?')) return;
                    await fetch('/api/gamebuilder/operators/' + id, { method: 'DELETE' });
                    loadPalette();
                }

                document.getElementById('operator-form').addEventListener('submit', async function(e) {
                    e.preventDefault();
                    var name = document.getElementById('operator-name').value.trim();
                    var arity = parseInt(document.getElementById('operator-arity').value);
                    if (!name) return;
                    if (slotSelections.length !== arity) {
                        slotSelections = [];
                        for (var i = 0; i < arity; i++) slotSelections.push([]);
                    }
                    var operands = slotSelections.map(function(s) { return s.slice(); });
                    var contract = getContractFromForm();
                    var el = document.getElementById('operator-status');
                    el.textContent = '...';
                    var res = await fetch('/api/gamebuilder/operators', {
                        method: 'POST', headers: {'Content-Type': 'application/json'},
                        body: JSON.stringify({ name: name, operandCount: arity, operands: operands, contract: contract })
                    });
                    if (res.ok) { el.innerHTML = '<span class="pill good">ok</span>'; document.getElementById('operator-name').value = ''; slotSelections = []; refreshSlotDisplays(); loadPalette(); }
                    else { var err = await res.json(); el.innerHTML = '<span class="pill bad">' + h(err.error) + '</span>'; }
                });

                document.getElementById('operator-arity').addEventListener('change', function() {
                    slotSelections = [];
                    renderOperatorSlots();
                    refreshSlotDisplays();
                });

                // ── Artifacts ───────────────────────────────────────────

                function renderEntityDropdown() {
                    var sel = document.getElementById('artifact-entity-id');
                    var opts = [];
                    paletteData.realms.forEach(function(r) { opts.push(`<option value="${r.id}">R: ${h(r.name)} (${r.id})</option>`); });
                    paletteData.components.forEach(function(c) { opts.push(`<option value="${c.id}">C: ${h(c.name)} (${c.id})</option>`); });
                    paletteData.operators.forEach(function(o) { opts.push(`<option value="${o.id}">F: ${h(o.name)} (${o.id})</option>`); });
                    sel.innerHTML = opts.join('');
                }

                async function loadArtifacts() {
                    try {
                        var res = await fetch('/api/gamebuilder/artifacts');
                        var artifacts = await res.json();
                        document.getElementById('artifact-count-label').textContent = artifacts.length;
                        if (artifacts.length === 0) {
                            document.getElementById('artifact-list').innerHTML = '<span class="muted small">none</span>';
                        } else {
                            document.getElementById('artifact-list').innerHTML = artifacts.map(function(a) {
                                var entityName = '';
                                if (paletteData) {
                                    var found = paletteData.realms.find(function(r) { return r.id === a.entityId; }) ||
                                                paletteData.components.find(function(c) { return c.id === a.entityId; }) ||
                                                paletteData.operators.find(function(o) { return o.id === a.entityId; });
                                    if (found) entityName = found.name;
                                }
                                return `<div class="entity-row"><code class="entity-id">${a.entityId}</code><span class="entity-name">${h(entityName)}</span><span class="muted small">${h(a.description)}</span><button class="btn-micro" onclick="removeArtifact('${a.entityId}')">\\u2715</button></div>`;
                            }).join('');
                        }
                    } catch(e) { console.error(e); }
                }

                async function removeArtifact(entityId) {
                    if (!confirm('Remove artifact for ' + entityId + '?')) return;
                    await fetch('/api/gamebuilder/artifacts/' + entityId, { method: 'DELETE' });
                    loadArtifacts();
                }

                document.getElementById('artifact-form').addEventListener('submit', async function(e) {
                    e.preventDefault();
                    var entityId = document.getElementById('artifact-entity-id').value;
                    var description = document.getElementById('artifact-description').value.trim();
                    if (!entityId || !description) return;
                    var el = document.getElementById('artifact-status');
                    el.textContent = '...';
                    var res = await fetch('/api/gamebuilder/artifacts', {
                        method: 'POST', headers: {'Content-Type': 'application/json'},
                        body: JSON.stringify({ entityId: entityId, description: description })
                    });
                    if (res.ok) { el.innerHTML = '<span class="pill good">ok</span>'; document.getElementById('artifact-description').value = ''; loadArtifacts(); }
                    else { var err = await res.json(); el.innerHTML = '<span class="pill bad">' + h(err.error) + '</span>'; }
                });

                // ── Composer + Control ──────────────────────────────────

                function renderComposeChecklists() {
                    var rhtml = paletteData.realms.map(function(r) {
                        return `<label><input type="checkbox" class="compose-cb-realm" value="${r.id}"> ${h(r.name)} <span class="muted">${r.id}</span></label>`;
                    }).join('');
                    document.getElementById('compose-realms').innerHTML = rhtml || '<span class="muted small">none</span>';

                    var chtml = paletteData.components.map(function(c) {
                        return `<label><input type="checkbox" class="compose-cb-comp" value="${c.id}"> ${h(c.name)} <span class="muted">${c.id}</span></label>`;
                    }).join('');
                    document.getElementById('compose-components').innerHTML = chtml || '<span class="muted small">none</span>';

                    var ohtml = paletteData.operators.map(function(o) {
                        return `<label><input type="checkbox" class="compose-cb-op" value="${o.id}"> ${h(o.name)} <span class="muted">${o.id}</span></label>`;
                    }).join('');
                    document.getElementById('compose-operators').innerHTML = ohtml || '<span class="muted small">none</span>';
                }

                function getComposeSelection() {
                    var realmIds = [];
                    document.querySelectorAll('.compose-cb-realm:checked').forEach(function(cb) { realmIds.push(cb.value); });
                    var componentIds = [];
                    document.querySelectorAll('.compose-cb-comp:checked').forEach(function(cb) { componentIds.push(cb.value); });
                    var operatorIds = [];
                    document.querySelectorAll('.compose-cb-op:checked').forEach(function(cb) { operatorIds.push(cb.value); });
                    return { realmIds: realmIds, componentIds: componentIds, operatorIds: operatorIds };
                }

                document.getElementById('compose-select-all-btn').addEventListener('click', function() {
                    document.querySelectorAll('.compose-cb-realm, .compose-cb-comp, .compose-cb-op').forEach(function(cb) { cb.checked = true; });
                });

                document.getElementById('compose-deselect-all-btn').addEventListener('click', function() {
                    document.querySelectorAll('.compose-cb-realm, .compose-cb-comp, .compose-cb-op').forEach(function(cb) { cb.checked = false; });
                });

                document.getElementById('compose-btn').addEventListener('click', async function() {
                    var id = randomDefId();
                    var sel = getComposeSelection();
                    var el = document.getElementById('compose-status');
                    el.textContent = 'Composing...';
                    var res = await fetch('/api/gamebuilder/compose', {
                        method: 'POST', headers: {'Content-Type': 'application/json'},
                        body: JSON.stringify({ id: id, realmIds: sel.realmIds, componentIds: sel.componentIds, operatorIds: sel.operatorIds })
                    });
                    if (res.ok) {
                        var result = await res.json();
                        el.innerHTML = '<span class="pill good">Done</span>';
                        renderComposeResult(result);
                    } else {
                        var err = await res.json();
                        el.innerHTML = '<span class="pill bad">' + h(err.error) + '</span>';
                        document.getElementById('compose-result').innerHTML = '';
                    }
                });

                document.getElementById('reset-palette-btn').addEventListener('click', async function() {
                    if (!confirm('Reset palette to Bomberman defaults? This replaces everything.')) return;
                    var el = document.getElementById('compose-status');
                    el.textContent = 'Resetting...';
                    var res = await fetch('/api/gamebuilder/palette/reset', { method: 'POST' });
                    if (res.ok) { el.innerHTML = '<span class="pill good">Reset to Bomberman</span>'; loadPalette(); }
                    else { el.innerHTML = '<span class="pill bad">Failed</span>'; }
                });

                document.getElementById('clear-palette-btn').addEventListener('click', async function() {
                    if (!confirm('Clear the entire palette? This cannot be undone.')) return;
                    var el = document.getElementById('compose-status');
                    el.textContent = 'Clearing...';
                    var res = await fetch('/api/gamebuilder/palette', { method: 'DELETE' });
                    if (res.ok) { el.innerHTML = '<span class="pill good">Cleared</span>'; loadPalette(); }
                    else { el.innerHTML = '<span class="pill bad">Failed</span>'; }
                });

                function renderComposeResult(result) {
                    var gd = result.gameDefinition;
                    var v = result.validation;
                    var html = `<div class="compose-result-card"><div class="card-body"><h3>${h(gd.id)}</h3>`;

                    if (v.valid) {
                        html += '<div class="success-box" style="margin-bottom:12px;">\\u2713 Coherent!</div>';
                    } else {
                        html += '<div class="error-list" style="margin-bottom:12px;"><strong>Issues:</strong><ul>';
                        v.errors.forEach(function(e) { html += `<li>${h(e)}</li>`; });
                        html += '</ul></div>';
                    }

                    html += '<div style="display:grid;grid-template-columns:1fr 1fr;gap:8px;font-size:13px;">';
                    html += `<div><span class="muted">Realms:</span> ${gd.realms.length}</div>`;
                    html += `<div><span class="muted">Components:</span> ${gd.components.length}</div>`;
                    html += `<div><span class="muted">Operators:</span> ${gd.operators.length}</div>`;
                    html += `<div><span class="muted">Artifacts:</span> ${gd.artifacts.length}</div>`;
                    html += '</div>';

                    var allEntityIds = [];
                    gd.realms.forEach(function(r) { allEntityIds.push(r.id); });
                    gd.components.forEach(function(c) { allEntityIds.push(c.id); });
                    gd.operators.forEach(function(o) { allEntityIds.push(o.id); });
                    var artifactIds = gd.artifacts.map(function(a) { return a.entityId; });
                    var missing = allEntityIds.filter(function(id) { return artifactIds.indexOf(id) < 0; });
                    if (missing.length > 0) {
                        html += '<div style="margin-top:8px;font-size:12px;color:var(--danger);">Missing artifacts for: ' + missing.map(function(id) {
                            var name = id;
                            var found = gd.realms.find(function(r) { return r.id === id; }) ||
                                        gd.components.find(function(c) { return c.id === id; }) ||
                                        gd.operators.find(function(o) { return o.id === id; });
                            if (found) name = found.name;
                            return `${h(name)} (${id})`;
                        }).join(', ') + '</div>';
                    }

                    html += '</div></div>';
                    document.getElementById('compose-result').innerHTML = html;
                }

                // ── Ask AI ──────────────────────────────────────────────

                document.getElementById('ask-ai-form').addEventListener('submit', async function(e) {
                    e.preventDefault();
                    var prompt = document.getElementById('ask-ai-prompt').value.trim();
                    if (!prompt) return;
                    var statusEl = document.getElementById('ask-ai-status');
                    var responseEl = document.getElementById('ask-ai-response');
                    statusEl.textContent = 'Thinking...';
                    responseEl.innerHTML = '';
                    try {
                        var res = await fetch('/api/gamebuilder/ask/ai', {
                            method: 'POST', headers: {'Content-Type': 'application/json'},
                            body: JSON.stringify({ prompt: prompt })
                        });
                        if (res.ok) {
                            var data = await res.json();
                            statusEl.innerHTML = '<span class="pill good">ok</span>';
                            responseEl.innerHTML = '<div class="success-box">' + h(data.message || JSON.stringify(data)) + '</div>';
                        } else {
                            var err = await res.json();
                            statusEl.innerHTML = '<span class="pill bad">' + h(err.error || 'Failed') + '</span>';
                        }
                    } catch(ex) {
                        statusEl.innerHTML = '<span class="pill bad">Network error</span>';
                    }
                });

                // ── Init ────────────────────────────────────────────────

                buildContractGrid();
                loadPalette();
                </script>
                """;

        return PageShell.render("Studio", "/", body);
    }
}
