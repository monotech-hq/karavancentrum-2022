(ns plugins.text-editor.view
  (:require
   ["react" :as react :default useMemo]
   [reagent.core :as r :refer [atom]]
   [jodit-react :default JoditEditor]
   [x.app-core.api :as a]
   [x.app-elements.api :as elements]))

;; -----------------------------------------------------------------------------
;; ---- Utils ----

(defn memofn
  ([f]
   (react/useMemo f #js []))
  ([f deps]
   (react/useMemo f (to-array deps))))

;; ---- Utils ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Configurations ----

(def default-settings {:language "hu"
                       :minHeight "400"
                       :cleanHTML true
                       :cleanWhitespace true})

;; ---- Configurations ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Utils ----

(defn get-props! [{:keys [settings on-change editor-value value-path editor-props]}]
  (let [config  (if settings settings default-settings)]
   (merge
      {:config   (clj->js config)
       :value    (or @(a/subscribe [:db/get-item value-path]) "")
       :onChange #(a/dispatch [:db/set-item! value-path %])}
      editor-props)))

;; ---- Utils ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Components ----

(defn sync-value [local-value db-value]
  (react/useEffect (fn []
                     (fn []
                       (println "useEffect")
                       (println "use-effect-render" @local-value db-value)
                       (println "=" (= @local-value db-value))
                       (reset! local-value db-value)))
                   (array (= @local-value db-value))))

(defn text-editor [{:keys [settings value-path local-value ditor-props change] :as props}]
  (let [config  (clj->js (if settings settings default-settings))
        value (sync-value local-value @(a/subscribe [:db/get-item value-path]))]
    [:div
     [:p (str (random-uuid))]
     [:div
       [:p "memo"(str (random-uuid))]
       [:> JoditEditor
         {;:config   config
          :value    @local-value;(memofn (fn [] (or @(a/subscribe [:db/get-item value-path]) "")))
          :onChange (fn [value]
                      (a/dispatch-sync [:db/set-item! value-path value]))
          :onBlur (fn [value]
                    (println "on-change")
                    (reset! local-value value)
                    (a/dispatch-sync [:db/set-item! value-path value]))}]]]))
       ; [(= @local-value @(a/subscribe [:db/get-item value-path]))]]]))
     ;(= @local-value @(a/subscribe [:db/get-item value-path]))])]))


(defn view [{:keys [value-path settings]}]
  ;  @param {:value-path   (keywords in vector)
  ;          :settings     (hash-map)(opt)                                      ;; optional settings if not included default-settings going to use
  ;          :editor-props (hash-map)(opt)}]                                    ;; optional props for JoditEditor in case have to overwrite something
  ;
  ; @usage [plugins/text-editor {:value-path [:my-value-path]})
  ;
  ; @return [reagent-component]}
  (assert value-path
          (str "Must provide :value-path in text-editor config"))
  (let [db-value      (or @(a/subscribe [:db/get-item value-path]) "")          ;; check reframe-db for value if nil return string to jodit
        local-value  (atom "")]                                         ;; load reframe-db value as init value
    (r/create-class
      {:reagent-render
       (fn [config]
         (let [editor-settings (assoc config
                                      :local-value local-value
                                      :change (= @local-value @(a/subscribe [:db/get-item value-path])))]
           [:div
            [:p "re-frame: " (str @(a/subscribe [:db/get-item value-path]))]
            [:p "local-value: " @local-value]
            [:p "changed?: " (str (:change editor-settings))]
            [:p (str (random-uuid))]
            [:f> text-editor editor-settings]]))})))
