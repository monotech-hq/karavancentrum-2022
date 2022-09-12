(ns plugins.text-editor.view
  (:require
   [reagent.api :refer [lifecycles ratom]]
   [jodit-react :default JoditEditor]
   [x.app-core.api :as a :refer [r]]
   [x.app-elements.api :as elements]))

;; -----------------------------------------------------------------------------
;; ---- Configurations ----

(def default-settings {:language "hu"
                       :minHeight "400"
                       :cleanHTML true
                       :cleanWhitespace true})

;; ---- Configurations ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Components ----

(defn text-editor [{:keys [settings value-path editor-value editor-props]}]
  (let [config  (if settings settings default-settings)
        on-blur (fn [value] (a/dispatch [:db/set-item! value-path value]))]
    [:> JoditEditor
     (merge {:config   config
             :value    @editor-value
             :tabIndex 1
             :onBlur   #(on-blur %)}                                            ;; set value on blur cause performance issues
            editor-props)]))

(defn view [{:keys [value-path settings]}]
  ;  @param {:value-path   (keywords in vector)
  ;          :settings     (hash-map)(opt)                                      ;; optional settings if not included default-settings going to use
  ;          :editor-props (hash-map)(opt)}]                                    ;; optional props for JoditEditor in case have to overwrite something
  ;
  ; @usage [plugins/text-editor {:value-path [:my-value-path]})
  ;
  ; @return [reagent-component]}
  (let [db-value      (or @(a/subscribe [:db/get-item value-path]) "")          ;; check reframe-db for value if nil return string to jodit
        editor-value  (ratom db-value)]                                         ;; load reframe-db value as init value
    (assert value-path
            (str "Must provide :value-path in text-editor config"))
    (lifecycles
      {:reagent-render
       (fn [config]
         (let [editor-settings (assoc config :editor-value editor-value)]
           [text-editor editor-settings]))})))
